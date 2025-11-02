package com.wddyxd.security.provider;


import com.wddyxd.security.pojo.LoginAuthenticationToken;
import com.wddyxd.security.pojo.SecurityUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: spring-security
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-28 18:18
 **/

@Component
public class PasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // PasswordAuthenticationProvider
    public PasswordAuthenticationProvider(
            @Qualifier("passwordUserDetailsService") UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginAuthenticationToken loginToken = (LoginAuthenticationToken) authentication;

        // 只处理密码登录类型
        if (!"password".equals(loginToken.getLoginType())) {
            return null;
        }

        String username = (String) loginToken.getPrincipal();
        String password = (String) loginToken.getCredentials();

        System.out.println("PasswordAuthenticationProvider.password");

        // 加载用户信息
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(username);

        // 验证密码
        if (!passwordEncoder.matches(password, securityUser.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        return new UsernamePasswordAuthenticationToken(securityUser, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}