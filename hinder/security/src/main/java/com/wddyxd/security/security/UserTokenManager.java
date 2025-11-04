package com.wddyxd.security.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wddyxd.common.constant.RedisKeyConstants;
import com.wddyxd.security.pojo.TokenInfo;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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


    //1 使用jwt根据用户信息生成token
    public String createToken(TokenInfo tokenInfo) {
        // 参数校验
        if (tokenInfo == null) {
            System.out.println("生成JWT失败：TokenInfo不能为空");
            throw new IllegalArgumentException("TokenInfo不能为空");
        }

        try {
            String tokenInfoJson = new ObjectMapper().writeValueAsString(tokenInfo);
            String token = Jwts.builder().setSubject(tokenInfoJson)
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                    .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                    .compressWith(CompressionCodecs.GZIP)
                    .compact();
            System.out.println("生成JWT成功：" + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("JWT生成异常"+e);
        }
    }





    //在redis存储token，同一用户只能存储3个token，超过三条将排挤最先添加的token
    public void addTokenInRedis(String token) {

        if (token == null) {
            System.out.println("添加Token失败：Token不能为空");
            throw new IllegalArgumentException("Token不能为空");
        }

        TokenInfo tokenInfo = getTokenInfoFromToken(token);

        if (tokenInfo == null) {
            System.out.println("添加Token失败：TokenInfo不能为空");
            throw new IllegalArgumentException("TokenInfo不能为空");
        }

        Long userId = tokenInfo.getId();
        Long timestamp = tokenInfo.getTimestamp();

        if (userId == null || timestamp == null) {
            System.out.println("添加Token失败：用户ID和timestamp不能为空");
            throw new IllegalArgumentException("用户ID和timestamp不能为空");
        }

        try {
            // 1. 生成用户的有序集合键
            String userTokenSetKey = RedisKeyConstants.USER_LOGIN_TOKEN_SET.key  + userId.toString();
            // 2. 生成当前 token 的字符串键
            String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN.key + timestamp.toString();

            // 3. 添加 timestamp 到用户的有序集合（score 为当前时间戳，用于排序）
            zSetOps.add(userTokenSetKey, timestamp, timestamp);

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
            System.out.println("添加Token成功：" + token);
        } catch (Exception e) {
            System.out.println("令牌存储redis异常，请稍后重试");
            throw new RuntimeException("令牌存储redis异常，请稍后重试",e);
        }
    }


    //续期token
    public void refreshTokenExpire(String token) {

        try {
            if (token == null) {
                System.out.println("刷新Token失败：Token不能为空");
                throw new IllegalArgumentException("Token不能为空");
            }

            TokenInfo tokenInfo = getTokenInfoFromToken(token);
            if (tokenInfo == null) {
                System.out.println("刷新Token失败：TokenInfo不能为空");
                throw new IllegalArgumentException("TokenInfo不能为空");
            }
            Long timestamp = tokenInfo.getTimestamp();
            if (timestamp == null) {
                System.out.println("刷新Token失败：timestamp不能为空");
                throw new IllegalArgumentException("timestamp不能为空");
            }

            String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN.key + timestamp.toString();
            boolean expireSuccess = redisTemplate.expire(tokenKey, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
            if (!expireSuccess) {
                System.out.println("刷新Token失败：Token已过期");
                throw new IllegalArgumentException("Token已过期");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("刷新Token失败：参数无效");
            throw e;
        } catch (Exception e) {
            System.out.println("刷新Token失败：Redis操作异常");
            throw new RuntimeException("刷新Token失败：Redis操作异常", e);
        }


    }


    //获取token
    public String getToken(String token) {

        try {
            if (token == null) {
                System.out.println("获取Token失败：Token不能为空");
                throw new IllegalArgumentException("Token不能为空");
            }

            TokenInfo tokenInfo = getTokenInfoFromToken(token);
            if (tokenInfo == null) {
                System.out.println("获取Token失败：TokenInfo不能为空");
                throw new IllegalArgumentException("TokenInfo不能为空");
            }
            Long timestamp = tokenInfo.getTimestamp();
            if (timestamp == null) {
                System.out.println("获取Token失败：timestamp不能为空");
                throw new IllegalArgumentException("timestamp不能为空");
            }
            String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN.key + timestamp.toString();

            return (String)redisTemplate.opsForValue().get(tokenKey);
        } catch (IllegalArgumentException ex) {
            System.out.println("获取Token失败：参数无效");
            throw ex;
        }catch (Exception e) {
            System.out.println("获取Token失败：Redis操作异常");
            throw new RuntimeException("获取Token失败：Redis操作异常", e);
        }
    }



    //判断这个token在redis中是否存在
    public boolean hasSameTokenInRedis(String token) {
        try {
            if (token == null) {
                System.out.println("判断Token存在性失败：Token不能为空");
                throw new IllegalArgumentException("Token不能为空");
            }
            String tokenInRedis = getToken(token);
            if(tokenInRedis != null) {
                boolean isSame = tokenInRedis.equals(token);
                System.out.println("判断Token存在性结果：" + isSame);
                return isSame;
            }
            return false;
        } catch (Exception e) {
            if (e instanceof io.jsonwebtoken.ExpiredJwtException ||
                    e instanceof io.jsonwebtoken.MalformedJwtException) {
                // 可容忍的异常：token解析失败（过期、格式错误）
                System.out.println("判断Token存在性失败");
                return false;
            } else {
                // 不可容忍的异常：Redis故障、空指针等，需告警但返回false（避免业务中断）
                System.out.println("判断Token存在性失败：Redis故障");
                return false;
            }
        }
    }



    //移除token
    public void removeToken(String token) {

        try {
            if(token == null|| token.isEmpty()){
                System.out.println("删除Token失败：Token不能为空");
                return;
            }

            TokenInfo tokenInfo = getTokenInfoFromToken(token);
            if (tokenInfo == null) {
                System.out.println("删除token失败：token解析结果为null" + token);
                return;
            }
            Long userId = tokenInfo.getId();
            Long timestamp = tokenInfo.getTimestamp();
            if (userId == null || timestamp == null) {
                System.out.println("删除token失败：tokenInfo中的userId或timestamp为null" + token);
                return;
            }

            String userTokenSetKey = RedisKeyConstants.USER_LOGIN_TOKEN_SET.key + userId.toString();
            String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN.key + timestamp.toString();
            // 从有序集合中删除
            Long removedFromZSet = zSetOps.remove(userTokenSetKey, timestamp);
            if (removedFromZSet == null || removedFromZSet == 0) {
                System.out.println("删除token失败：未找到对应元素" + token);
                return;
            }
            // 删除 token 字符串键
            Boolean deletedToken = redisTemplate.delete(tokenKey);
            if (deletedToken == null || !deletedToken) {
                System.out.println("删除token失败：token不存在或已过期" + token);
                return;
            }
            System.out.println("删除token成功：" + token);
        } catch (Exception ex) {
            throw new RuntimeException("删除token异常"+ex);
        }
    }


    //从token中获取tokenInfo
    public TokenInfo getTokenInfoFromToken(String token) {

        if(token == null) {
            System.out.println("从token中获取TokenInfo失败：Token不能为空");
            return null;
        }

        try {
            String tokenInfoJson = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if(tokenInfoJson == null|| tokenInfoJson.isEmpty()) {
                System.out.println("从token中获取TokenInfo失败：tokenInfoJson为null");
                return null;
            }

            TokenInfo tokenInfo = new ObjectMapper().readValue(tokenInfoJson, TokenInfo.class);
            System.out.println("从token中获取TokenInfo成功：" + tokenInfo);
            if(tokenInfo != null)return tokenInfo;
            else throw new Exception("null token");
        } catch (Exception e) {
            e.printStackTrace();
            // 处理其他异常
            throw new RuntimeException("获取tokenInfo异常"+e);
        }
    }

}




