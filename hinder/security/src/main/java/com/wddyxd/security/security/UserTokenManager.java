package com.wddyxd.security.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.constant.RedisKeyConstants;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.security.exception.SecurityAuthException;
import com.wddyxd.security.pojo.TokenInfo;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: items-assigner
 * @description: JWT工具类
 * @author: wddyxd
 * @create: 2025-10-20 20:28
 **/

@Component
public class UserTokenManager {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ZSetOperations<String, Object> zSetOps;
    // 注入 RedisTemplate
    public UserTokenManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOps = redisTemplate.opsForZSet();
    }
    //token有效时长
    @Value("${token.expiration}")
    private long tokenExpiration;
    //编码秘钥
    @Value("${token.signKey}")
    private String tokenSignKey;
    //在redis 中 token 过期时间（60 分钟）
    private static final long TOKEN_EXPIRE_MINUTES = 60;
    // 每个用户最大 token 数量
    private static final int MAX_TOKEN_PER_USER = 3;
    private static final Logger log = LoggerFactory.getLogger(UserTokenManager.class);
    //1 使用jwt根据用户信息生成token
    public String createToken(TokenInfo tokenInfo) {
        // 参数校验
        if (tokenInfo == null) {
            log.error(LogPrompt.PARAM_EMPTY_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
        try {
            String tokenInfoJson = new ObjectMapper().writeValueAsString(tokenInfo);
            String token = Jwts.builder().setSubject(tokenInfoJson)
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                    .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                    .compressWith(CompressionCodecs.GZIP)
                    .compact();
            log.info(LogPrompt.SUCCESS_INFO.msg);
            return token;
        } catch (Exception e) {
            log.error(LogPrompt.IOEXCEPTION_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
    }
    //在redis存储token，同一用户只能存储3个token，超过三条将排挤最先添加的token
    public void addTokenInRedis(String token) {
        TokenInfo tokenInfo = IsValidToken(token);
        //TODO可用lua脚本解决高并发时超出token数量和事务问题
        try {
            // 1. 生成用户的有序集合键
            String userTokenSetKey = RedisKeyConstants.USER_LOGIN_TOKEN_SET.key  + tokenInfo.getId().toString();
            // 2. 生成当前 token 的字符串键
            String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN.key + tokenInfo.getTimestamp().toString();
            // 3. 添加 timestamp 到用户的有序集合（score 为当前时间戳，用于排序）
            zSetOps.add(userTokenSetKey, tokenInfo.getTimestamp(), tokenInfo.getTimestamp());
            // 4. 存储 token 对应的 value，并设置 15 分钟过期
            redisTemplate.opsForValue().set(tokenKey, token, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
            // 5. 检查集合大小，超过 3 个则删除最早的 token
            Long tokenCount = zSetOps.size(userTokenSetKey);
            if (tokenCount != null && tokenCount > MAX_TOKEN_PER_USER) {
                // 取最早的 1 个 token（按 score 升序，取第一个）
                Set<Object> oldestTimestamps = zSetOps.range(userTokenSetKey, 0, 0);
                if (oldestTimestamps != null && !oldestTimestamps.isEmpty()) {
                    Long oldestTimestamp = (Long) oldestTimestamps.iterator().next();
                    // 删除有序集合中的旧 token
                    zSetOps.remove(userTokenSetKey, oldestTimestamp);
                    // 删除旧 token 对应的字符串键（使其立即失效）
                    redisTemplate.delete(RedisKeyConstants.USER_LOGIN_TOKEN.key+ oldestTimestamp.toString());
                }
            }
            log.info(LogPrompt.SUCCESS_INFO.msg);
        } catch (Exception e) {
            log.error(LogPrompt.REDIS_SERVER_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
    }
    //续期token
    public void refreshTokenExpire(String token) {
        TokenInfo tokenInfo = IsValidToken(token);
        try {
            String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN.key + tokenInfo.getTimestamp().toString();
            boolean expireSuccess = redisTemplate.expire(tokenKey, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
            if (!expireSuccess) {
                log.error(LogPrompt.TOKEN_EXPIRED_INFO.msg);
                throw new SecurityAuthException(ResultCodeEnum.TOKEN_EXPIRED_ERROR);
            }
            log.info(LogPrompt.SUCCESS_INFO.msg);
        } catch (Exception e) {
            log.error(LogPrompt.REDIS_SERVER_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }


    }
    //获取token
    public String getToken(String token) {
        TokenInfo tokenInfo = IsValidToken(token);
        try {
            String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN.key + tokenInfo.getTimestamp().toString();
            return (String)redisTemplate.opsForValue().get(tokenKey);
        }catch (Exception e) {
            log.error(LogPrompt.REDIS_SERVER_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
    }
    //判断这个token在redis中是否存在
    public boolean hasSameTokenInRedis(String token) {
        if (token == null) {
            log.error(LogPrompt.PARAM_EMPTY_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
        String tokenInRedis = getToken(token);
        if(tokenInRedis != null) {
            boolean isSame = tokenInRedis.equals(token);
            log.info(LogPrompt.SUCCESS_INFO.msg);
            if(!isSame) throw new SecurityAuthException(ResultCodeEnum.TOKEN_EXPIRED_ERROR);
            else return true;
        }else throw new SecurityAuthException(ResultCodeEnum.TOKEN_EXPIRED_ERROR);
    }
    //移除token
    public void removeToken(String token) {
        TokenInfo tokenInfo = IsValidToken(token);
        try {
            String userTokenSetKey = RedisKeyConstants.USER_LOGIN_TOKEN_SET.key + tokenInfo.getId().toString();
            String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN.key + tokenInfo.getTimestamp().toString();
            // 从有序集合中删除
            Long removedFromZSet = zSetOps.remove(userTokenSetKey, tokenInfo.getTimestamp());
            if (removedFromZSet == null || removedFromZSet == 0) {
                log.warn(LogPrompt.DELETE_UNEXIST_INFO.msg);
            }
            // 删除 token 字符串键
            Boolean deletedToken = redisTemplate.delete(tokenKey);
            if (!deletedToken) {
                log.warn(LogPrompt.DELETE_UNEXIST_INFO.msg);
            }
            log.info(LogPrompt.SUCCESS_INFO.msg);
        } catch (Exception ex) {
            log.error(LogPrompt.REDIS_SERVER_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
    }
    //从token中获取tokenInfo
    public TokenInfo getTokenInfoFromToken(String token) {
        if (token == null) {
            log.error(LogPrompt.PARAM_EMPTY_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
        try {
            String tokenInfoJson = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if(tokenInfoJson == null|| tokenInfoJson.isEmpty()) {
                throw new SecurityAuthException(ResultCodeEnum.TOKEN_FORMAT_ERROR);
            }
            TokenInfo tokenInfo = new ObjectMapper().readValue(tokenInfoJson, TokenInfo.class);
            log.info(LogPrompt.SUCCESS_INFO.msg);
            if(tokenInfo != null)return tokenInfo;
            else throw new SecurityAuthException(ResultCodeEnum.TOKEN_FORMAT_ERROR);
        } catch (Exception e) {
            log.error(LogPrompt.TOKEN_FORMAT_ERROR.msg);
            if(e instanceof SecurityAuthException){
                throw (SecurityAuthException) e;
            }
            else throw new SecurityAuthException(ResultCodeEnum.IOEXCEPTION_ERROR);
        }
    }
    private TokenInfo IsValidToken(String token) {
        //TODO随着参数变多,方法要改进
        if (token == null) {
            log.error(LogPrompt.PARAM_EMPTY_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
        TokenInfo tokenInfo = getTokenInfoFromToken(token);
        if (tokenInfo == null) {
            log.error(LogPrompt.PARAM_EMPTY_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
        Long userId = tokenInfo.getId();
        Long timestamp = tokenInfo.getTimestamp();
        if (userId == null || timestamp == null) {
            log.error(LogPrompt.PARAM_EMPTY_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
        return tokenInfo;
    }
}
