package com.wddyxd.security.provider;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.security.exception.SecurityAuthException;
import com.wddyxd.security.pojo.LoginAuthenticationToken;
import com.wddyxd.security.pojo.SecurityUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-02 14:25
 **/


@Component
public class EmailCodeAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    // EmailAuthenticationProvider
    public EmailCodeAuthenticationProvider(
            @Qualifier("emailCodeUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginAuthenticationToken customToken = (LoginAuthenticationToken) authentication;

//         只处理短信登录类型
        if (!"email".equals(customToken.getLoginType())) {
            return null;
        }
        String email = (String) customToken.getPrincipal();
        String emailCode = (String) customToken.getCredentials();
        // 加载用户信息
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(email);

        if(!securityUser.getLoginUserForm().getEmailCode().equals(emailCode))
            throw new SecurityAuthException(ResultCodeEnum.CODE_ERROR);
        return new UsernamePasswordAuthenticationToken(securityUser, null, null);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
