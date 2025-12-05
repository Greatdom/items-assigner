package com.wddyxd.userservice.pojo.DTO;


/**
 * @program: items-assigner
 * @description: 用户端用户注册接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 12:21
 **/

public class CustomUserRegisterDTO {

    private String username;

    private String phone;

    private String phoneCode;

    private String email;

    private String emailCode;

    private String password;

    @Override
    public String toString() {
        return "CustomUserRegisterDTO{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", email='" + email + '\'' +
                ", emailCode='" + emailCode + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
