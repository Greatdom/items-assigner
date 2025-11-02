package com.wddyxd.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wddyxd.common.constant.RedisKeyConstants;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.security.pojo.SecurityUser;
import com.wddyxd.security.pojo.LoginUserForm;
import com.wddyxd.security.pojo.TokenInfo;
import com.wddyxd.security.pojo.LoginAuthenticationToken;
import com.wddyxd.security.security.UserTokenManager;
import com.wddyxd.common.utils.ResponseUtil;
import com.wddyxd.common.utils.Result;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/**
 * @program: items-assigner
 * @description: 用户认证时的拦截器，包括获取前端表单数据，认证成功逻辑和认证失败逻辑实现
 * @author: wddyxd
 * @create: 2025-10-20 20:33
 **/

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private UserTokenManager userTokenManager;
    private RedisTemplate redisTemplate;
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter(AuthenticationManager authenticationManager, UserTokenManager userTokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.userTokenManager = userTokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/auth/login","POST"));
    }

    //1 获取表单提交用户名和密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        //获取表单提交数据
        try {
            LoginUserForm user = new ObjectMapper().readValue(request.getInputStream(), LoginUserForm.class);
             if(StringUtils.isEmpty(user.getLoginType())){
                 user.setLoginType("password");
             }
             if("phone".equals(user.getLoginType())){
                 return authenticationManager.authenticate(
                         new LoginAuthenticationToken(user.getPhone(),user.getPhoneCode(),"phone",
                         null));
             }else if("email".equals(user.getLoginType())){
                 return authenticationManager.authenticate(
                         new LoginAuthenticationToken(user.getEmail(),user.getEmailCode(),"email",
                         null));

             }else if("password".equals(user.getLoginType())){
                 String principal = org.springframework.util.StringUtils.hasText(user.getUsername()) ?
                         user.getUsername() : user.getPhone();
                 principal = org.springframework.util.StringUtils.hasText(principal) ?
                         principal : user.getEmail();
                 return authenticationManager.authenticate(
                         new LoginAuthenticationToken(principal,user.getPassword(),"password",
                         null));

             }else{
                 throw new RuntimeException("请选择正确的登录方式");
             }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取表单错误");
        }
    }

    //2 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        //认证成功，得到认证成功之后用户信息
        SecurityUser user = (SecurityUser)authResult.getPrincipal();
        System.out.println("用户信息："+user.getCurrentUserInfo());
        //根据用户名生成token
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setId(user.getCurrentUserInfo().getId());
        String token = userTokenManager.createToken(tokenInfo);
        System.out.println("用户token："+token);



        //把用户名称和用户信息放到redis
        redisTemplate.opsForValue().set(RedisKeyConstants.USER_LOGIN_USERINFO + user.getCurrentUserInfo().getId().toString(),user.getCurrentUserInfo());
        //返回token
        ResponseUtil.out(response, Result.success(token));
    }

    //3 认证失败调用的方法
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        ResponseUtil.out(response, Result.error(ResultCodeEnum.AUTH_FAILED_ERROR));
    }
}
