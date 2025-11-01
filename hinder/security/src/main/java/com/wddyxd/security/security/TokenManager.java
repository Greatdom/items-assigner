package com.wddyxd.security.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wddyxd.security.pojo.CurrentUserInfo;
import com.wddyxd.security.pojo.TokenInfo;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: items-assigner
 * @description: JWT工具类
 * @author: wddyxd
 * @create: 2025-10-20 20:28
 **/

@Component
public class TokenManager {
    //token有效时长
    private long tokenEcpiration = 24*60*60*1000;
    //编码秘钥
    private String tokenSignKey = "123456";
//    //1 使用jwt根据用户名生成token
//    public String createToken(CurrentUserInfo) {
//
//        String token = Jwts.builder().setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis()+tokenEcpiration))
//                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
//        return token;
//    }
    //1 使用jwt根据用户信息生成token
    public String createToken(TokenInfo tokenInfo) {

        try {
            String tokenInfoJson = new ObjectMapper().writeValueAsString(tokenInfo);

            String token = Jwts.builder().setSubject(tokenInfoJson)
                    .setExpiration(new Date(System.currentTimeMillis() + tokenEcpiration))
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
