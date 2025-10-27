package com.wddyxd.security.pojo;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @program: items-assigner
 * @description: spring-security内置认证授权用户实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:24
 **/

public class SecurityUser implements UserDetails {

    //当前登录用户
    private transient LoginUserForm loginUserForm;


    public LoginUserForm getLoginUserForm() {
        return loginUserForm;
    }

    public void setLoginUserForm(LoginUserForm loginUserForm) {
        this.loginUserForm = loginUserForm;
    }



    //当前权限
    private CurrentUserInfo currentUserInfo;
    public CurrentUserInfo getCurrentUserInfo() {
        return currentUserInfo;
    }

    public void setCurrentUserInfo(CurrentUserInfo currentUserInfo) {
        this.currentUserInfo = currentUserInfo;
    }


    public SecurityUser() {
    }

    public SecurityUser(LoginUserForm loginUserForm, CurrentUserInfo currentUserDTO) {
        this.loginUserForm = loginUserForm;
        this.currentUserInfo = currentUserDTO;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<String> permissionValueList = currentUserInfo.getPermissionValueList();
        for(String permissionValue : permissionValueList) {
            if(StringUtils.isEmpty(permissionValue)) continue;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
            authorities.add(authority);
        }

        return authorities;
    }



    @Override
    public String getPassword() {
        return loginUserForm.getPassword();
    }

    @Override
    public String getUsername() {
        return loginUserForm.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}