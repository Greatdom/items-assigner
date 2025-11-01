package com.wddyxd.security.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-29 01:07
 **/

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal; // 用户名或手机号
    private String credentials; // 密码或验证码
    private final String loginType; // 登录类型

    // 未认证的构造函数
    public CustomAuthenticationToken(String principal, String credentials, String loginType) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.loginType = loginType;
        setAuthenticated(false);
    }

    // 已认证的构造函数
    public CustomAuthenticationToken(String  principal, String credentials, String loginType,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.loginType = loginType;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getLoginType() {
        return loginType;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}