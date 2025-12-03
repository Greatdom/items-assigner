package com.wddyxd.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.security.exception.SecurityAuthException;
import com.wddyxd.security.pojo.SecurityUser;
import com.wddyxd.security.pojo.LoginUserForm;
import com.wddyxd.security.pojo.TokenInfo;
import com.wddyxd.security.pojo.LoginAuthenticationToken;
import com.wddyxd.security.security.UserInfoManager;
import com.wddyxd.security.security.UserTokenManager;
import com.wddyxd.common.utils.ResponseUtil;
import com.wddyxd.common.utils.Result;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

/**
 * @program: items-assigner
 * @description: 用户认证时的拦截器，包括获取前端表单数据，认证成功逻辑和认证失败逻辑实现
 * @author: wddyxd
 * @create: 2025-10-20 20:33
 **/

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final UserInfoManager userInfoManager;
    private final UserTokenManager userTokenManager;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFailureHandler AuthFailureHandler;
    private static final Logger log = LoggerFactory.getLogger(TokenLoginFilter.class);

    public TokenLoginFilter(AuthenticationManager authenticationManager ,
                            UserTokenManager userTokenManager,
                            UserInfoManager userInfoManager,
                            AuthenticationFailureHandler AuthFailureHandler) {
        this.userInfoManager = userInfoManager;
        this.authenticationManager = authenticationManager;
        this.userTokenManager = userTokenManager;
        this.AuthFailureHandler = AuthFailureHandler;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/auth/login", "POST"));
    }

    //获取登录表单
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        //获取表单提交数据
        try {
            LoginUserForm user = new ObjectMapper().readValue(request.getInputStream(), LoginUserForm.class);
             if(StringUtils.isEmpty(user.getLoginType())){
                 user.setLoginType("password");
             }
            switch (user.getLoginType()) {
                case "phone" -> {
                    return authenticationManager.authenticate(
                            new LoginAuthenticationToken(user.getPhone(), user.getPhoneCode(), "phone",
                                    null));
                }
                case "email" -> {
                    return authenticationManager.authenticate(
                            new LoginAuthenticationToken(user.getEmail(), user.getEmailCode(), "email",
                                    null));
                }
                case "password" -> {
                    String principal = org.springframework.util.StringUtils.hasText(user.getUsername()) ?
                            user.getUsername() : user.getPhone();
                    principal = org.springframework.util.StringUtils.hasText(principal) ?
                            principal : user.getEmail();
                    return authenticationManager.authenticate(
                            new LoginAuthenticationToken(principal, user.getPassword(), "password",
                                    null));
                }
                case null, default -> throw new SecurityAuthException(ResultCodeEnum.LOGIN_FORM_ERROR);
            }
        } catch (IOException e) {
            log.error(LogPrompt.IOEXCEPTION_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
    }

    //2 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)throws IOException, ServletException {
        //认证成功，得到认证成功之后用户信息
        SecurityUser user = (SecurityUser)authResult.getPrincipal();
        System.out.println("用户信息："+user.getCurrentUserInfo());
        //TODO根据用户名生成token,传入id,时间戳,设备及浏览器信息,客户端公网 IP,客户端局域网 IP,客户端名称
        //TODO将这段逻辑集成一个方法
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setId(user.getCurrentUserInfo().getId());
        tokenInfo.setTimestamp(System.currentTimeMillis());
        String token = null;

        try {
            token = userTokenManager.createToken(tokenInfo);
            System.out.println("用户token："+token);
            userTokenManager.addTokenInRedis(token);
            //把用户名称和用户信息放到redis
            userInfoManager.saveInfoInRedis(user);
        } catch (AuthenticationException e){
            this.unsuccessfulAuthentication(request, response, e);
        }
        //返回token
        ResponseUtil.out(response, Result.success(token));
    }

    //3 认证失败调用的方法
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        AuthFailureHandler.onAuthenticationFailure(request, response, failed);
    }
}
