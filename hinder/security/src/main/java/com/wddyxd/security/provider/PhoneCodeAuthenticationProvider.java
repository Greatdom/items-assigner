package com.wddyxd.security.provider;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.utils.FlexibleCodeCheckerService;
import com.wddyxd.security.exception.SecurityAuthException;
import com.wddyxd.security.pojo.LoginAuthenticationToken;
import com.wddyxd.security.pojo.SecurityUser;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final FlexibleCodeCheckerService flexibleCodeCheckerService;

    // PhoneAuthenticationProvider
    public PhoneCodeAuthenticationProvider(
            @Qualifier("phoneCodeUserDetailsService") UserDetailsService userDetailsService,
            FlexibleCodeCheckerService flexibleCodeCheckerService

    ) {
        this.userDetailsService = userDetailsService;
        this.flexibleCodeCheckerService = flexibleCodeCheckerService;
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

        // 加载用户信息
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(phone);
        // 校验手机验证码
        if (flexibleCodeCheckerService.checkPhoneCodeWrong(phone, phoneCode))
            throw new SecurityAuthException(ResultCodeEnum.CODE_ERROR);
        return new UsernamePasswordAuthenticationToken(securityUser, null, null);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }

}