package com.wddyxd.security.provider;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.security.pojo.LoginAuthenticationToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: spring-security
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-28 18:20
 **/

@Component
public class PhoneCodeAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // PhoneAuthenticationProvider
    public PhoneCodeAuthenticationProvider(
            @Qualifier("phoneCodeUserDetailsService") UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        // 注意：短信登录可能不需要密码编码器，可以根据需要调整
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginAuthenticationToken customToken = (LoginAuthenticationToken) authentication;

//         只处理短信登录类型
        if (!"phone".equals(customToken.getLoginType())) {
            return null;
        }
        String phone = (String) customToken.getPrincipal();
        String phoneCode = (String) customToken.getCredentials();

        System.out.println("PhoneAuthenticationProvider.phone");
        // 加载用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
        throw new RuntimeException(ResultCodeEnum.FUNCTION_ERROR.msg);
        // 创建已认证的token
//        return new LoginAuthenticationToken("smswww", code, "sms", userDetails.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

}