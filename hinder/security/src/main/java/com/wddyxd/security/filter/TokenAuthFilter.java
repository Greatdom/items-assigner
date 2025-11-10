package com.wddyxd.security.filter;


import com.wddyxd.common.constant.RedisKeyConstants;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.security.exception.SecurityAuthException;
import com.wddyxd.security.pojo.CurrentUserInfo;
import com.wddyxd.security.pojo.TokenInfo;
import com.wddyxd.security.security.UserInfoManager;
import com.wddyxd.security.security.UserTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @program: items-assigner
 * @description: 用户认证后发起请求时对token进行校验的过滤器实现，包括解析token功能
 * @author: wddyxd
 * @create: 2025-10-20 20:36
 **/

public class TokenAuthFilter extends BasicAuthenticationFilter {

    private final UserTokenManager userTokenManager;
    private final UserInfoManager userInfoManager;
    private final AuthenticationEntryPoint unAuthEntryPoint;
    public TokenAuthFilter(AuthenticationManager authenticationManager,
                           UserTokenManager userTokenManager,
                           UserInfoManager userInfoManager,
                           AuthenticationEntryPoint unAuthEntryPoint) {
        super(authenticationManager);
        this.userTokenManager = userTokenManager;
        this.userInfoManager = userInfoManager;
        this.unAuthEntryPoint = unAuthEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/user/auth/passwordSecurityGetter")||
             requestURI.startsWith("/swagger-ui")||
            requestURI.startsWith("/v3/api-docs")||
            requestURI.startsWith("/actuator")||
            requestURI.startsWith( "/sentinel")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authRequest);
            chain.doFilter(request,response);
        } catch (SecurityAuthException e) {
            unAuthEntryPoint.commence(request, response, e);
        }

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 从header获取token
        String token = request.getHeader("token");
        // 判断token是否为空
        if(token == null||token.isEmpty()) {
            throw new SecurityAuthException(ResultCodeEnum.UN_LOGIN_ERROR);
        }
        // 判断token是否在redis中,也就是判断token是否过期
        boolean isSame = userTokenManager.hasSameTokenInRedis(token);
        if(!isSame) throw new SecurityAuthException(ResultCodeEnum.TOKEN_EXPIRED_ERROR);
        // 刷新token的过期时间
        userTokenManager.refreshTokenExpire(token);
        // 从token中获取用户信息
        TokenInfo tokenInfo = userTokenManager.getTokenInfoFromToken(token);
        Long id = tokenInfo.getId();
        // 从redis获取用户信息
        CurrentUserInfo redisUserInfo = userInfoManager.getInfoFromRedis(id);
        if (redisUserInfo != null) {
            List<String> permissionValueList = redisUserInfo.getPermissionValueList();
            Collection<GrantedAuthority> authority = new ArrayList<>();
            for(String permissionValue : permissionValueList) {
                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permissionValue);
                authority.add(auth);
            }
            return new UsernamePasswordAuthenticationToken(redisUserInfo, token, authority);
        }else throw new SecurityAuthException(ResultCodeEnum.USER_INFO_ERROR);
    }

}