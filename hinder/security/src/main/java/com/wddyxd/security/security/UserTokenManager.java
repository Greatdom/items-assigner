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

    //在redis 中 token 过期时间（15 分钟）
    private static final long TOKEN_EXPIRE_MINUTES = 15;
    // 每个用户最大 token 数量
    private static final int MAX_TOKEN_PER_USER = 3;


    //1 使用jwt根据用户信息生成token
    public String createToken(TokenInfo tokenInfo) {

        try {
            String tokenInfoJson = new ObjectMapper().writeValueAsString(tokenInfo);
            String token = Jwts.builder().setSubject(tokenInfoJson)
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                    .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                    .compressWith(CompressionCodecs.GZIP)
                    .compact();
            return token;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //在redis存储token，同一用户只能存储3个token，超过三条将排挤最先添加的token
    public void addTokenInRedis(String token) {

        TokenInfo tokenInfo = getTokenInfoFromToken(token);
        Long userId = tokenInfo.getId();
        Long timestamp = tokenInfo.getTimestamp();


        // 1. 生成用户的有序集合键
        String userTokenSetKey = RedisKeyConstants.USER_LOGIN_TOKEN_SET  + userId.toString();
        // 2. 生成当前 token 的字符串键
        String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN + timestamp.toString();

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
                redisTemplate.delete(RedisKeyConstants.USER_LOGIN_TOKEN+ oldestTimestamp.toString());
            }
        }
    }


    //续期token
    public void refreshTokenExpire(String token) {

        TokenInfo tokenInfo = getTokenInfoFromToken(token);
        Long timestamp = tokenInfo.getTimestamp();

        String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN + timestamp.toString();
        // 检查 token 是否存在，存在则刷新过期时间
        if (redisTemplate.hasKey(tokenKey)) {
            redisTemplate.expire(tokenKey, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);

        }

    }

    //获取token
    public String getToken(String token) {

        TokenInfo tokenInfo = getTokenInfoFromToken(token);
        Long timestamp = tokenInfo.getTimestamp();
        String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN + timestamp.toString();

        return (String)redisTemplate.opsForValue().get(tokenKey);
    }

    //判断这个token在redis中是否存在
    public boolean hasSameTokenInRedis(String token) {
        String tokenInRedis = getToken(token);
        if(tokenInRedis != null)
            return tokenInRedis.equals(token);
        return false;
    }

    //移除token
    public void removeToken(String token) {

        TokenInfo tokenInfo = getTokenInfoFromToken(token);
        Long userId = tokenInfo.getId();
        Long timestamp = tokenInfo.getTimestamp();

        String userTokenSetKey = RedisKeyConstants.USER_LOGIN_TOKEN_SET + userId.toString();
        String tokenKey = RedisKeyConstants.USER_LOGIN_TOKEN + timestamp.toString();
        // 从有序集合中删除
        zSetOps.remove(userTokenSetKey, token);
        // 删除 token 字符串键
        redisTemplate.delete(tokenKey);
    }

    //从token中获取tokenInfo
    public TokenInfo getTokenInfoFromToken(String token) {
        String tokenInfoJson = Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        try {
            return new ObjectMapper().readValue(tokenInfoJson, TokenInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常
            return null;
        }
    }
}




