package com.wddyxd.userservice.pojo.securityDTO;


import java.io.Serializable;

/**
 * @program: items-assigner
 * @description: spring-security认证时用到的用户实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:23
 **/

public class LoginUserForm implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;

    private String phone;
    private String phoneCode;

    private String email;
    private String emailCode;

    private String loginType;

    private String client;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}