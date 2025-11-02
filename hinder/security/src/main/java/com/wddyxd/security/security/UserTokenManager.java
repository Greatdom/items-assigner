package com.wddyxd.security.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wddyxd.security.pojo.TokenInfo;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: items-assigner
 * @description: JWT工具类
 * @author: wddyxd
 * @create: 2025-10-20 20:28
 **/

@Component
public class UserTokenManager {
    //token有效时长
    @Value("${token.expiration}")
    private long tokenExpiration;
    //编码秘钥
    @Value("${token.signKey}")
    private String tokenSignKey;

//    //token有效时长
//    private long tokenEcpiration = 24*60*60*1000;
//    //编码秘钥
//    private String tokenSignKey = "123456";
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

    //3 删除token
    public void removeToken(String token) { }
}


//public class TokenRedisManager {
//
//    // token 字符串键的前缀（存储 token 对应的用户信息）
//    private static final String TOKEN_KEY_PREFIX = "token:";
//    // 用户 token 集合的键前缀（有序集合，记录用户的所有 token）
//    private static final String USER_TOKEN_SET_PREFIX = "token:user:";
//    // token 过期时间（15 分钟）
//    private static final long TOKEN_EXPIRE_MINUTES = 15;
//    // 每个用户最大 token 数量
//    private static final int MAX_TOKEN_PER_USER = 3;
//
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final ZSetOperations<String, Object> zSetOps;
//
//    // 注入 RedisTemplate
//    public TokenRedisManager(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        this.zSetOps = redisTemplate.opsForZSet();
//    }
//
//    /**
//     * 新增用户的 token（自动维护最多 3 个，剔除最早的）
//     * @param userId 用户 ID
//     * @param token token 值
//     * @param tokenValue token 对应的存储值（如用户信息）
//     */
//    public void addToken(Long userId, String token, Object tokenValue) {
//        // 1. 生成用户的有序集合键
//        String userTokenSetKey = USER_TOKEN_SET_PREFIX + userId;
//        // 2. 生成当前 token 的字符串键
//        String tokenKey = TOKEN_KEY_PREFIX + token;
//
//        // 3. 添加 token 到用户的有序集合（score 为当前时间戳，用于排序）
//        long currentTimestamp = System.currentTimeMillis();
//        zSetOps.add(userTokenSetKey, token, currentTimestamp);
//
//        // 4. 存储 token 对应的 value，并设置 15 分钟过期
//        redisTemplate.opsForValue().set(tokenKey, tokenValue, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
//
//        // 5. 检查集合大小，超过 3 个则删除最早的 token
//        Long tokenCount = zSetOps.size(userTokenSetKey);
//        if (tokenCount != null && tokenCount > MAX_TOKEN_PER_USER) {
//            // 取最早的 1 个 token（按 score 升序，取第一个）
//            Set<Object> oldestTokens = zSetOps.range(userTokenSetKey, 0, 0);
//            if (oldestTokens != null && !oldestTokens.isEmpty()) {
//                String oldestToken = (String) oldestTokens.iterator().next();
//                // 删除有序集合中的旧 token
//                zSetOps.remove(userTokenSetKey, oldestToken);
//                // 删除旧 token 对应的字符串键（使其立即失效）
//                redisTemplate.delete(TOKEN_KEY_PREFIX + oldestToken);
//            }
//        }
//    }
//
//    /**
//     * 刷新当前 token 的过期时间（延长至 15 分钟）
//     * @param token 当前携带的 token
//     * @return 是否刷新成功（token 存在则成功）
//     */
//    public boolean refreshTokenExpire(String token) {
//        String tokenKey = TOKEN_KEY_PREFIX + token;
//        // 检查 token 是否存在，存在则刷新过期时间
//        if (redisTemplate.hasKey(tokenKey)) {
//            redisTemplate.expire(tokenKey, TOKEN_EXPIRE_MINUTES, TimeUnit.MINUTES);
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 根据 token 获取对应的存储值（如用户信息）
//     * @param token token 值
//     * @return token 对应的 value（null 表示 token 不存在或已过期）
//     */
//    public Object getTokenValue(String token) {
//        return redisTemplate.opsForValue().get(TOKEN_KEY_PREFIX + token);
//    }
//
//    /**
//     * 手动删除用户的某个 token（如退出登录）
//     * @param userId 用户 ID
//     * @param token 要删除的 token
//     */
//    public void removeToken(Long userId, String token) {
//        String userTokenSetKey = USER_TOKEN_SET_PREFIX + userId;
//        String tokenKey = TOKEN_KEY_PREFIX + token;
//        // 从有序集合中删除
//        zSetOps.remove(userTokenSetKey, token);
//        // 删除 token 字符串键
//        redisTemplate.delete(tokenKey);
//    }
//}
