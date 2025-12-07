package com.wddyxd.userservice.pojo.DTO;


/**
 * @program: items-assigner
 * @description: 根据验证码找回密码接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 12:34
 **/

public class RebuildPasswordDTO {
    private String username;
    private String phone;
    private String phoneCode;
    private String newPassword;

    @Override
    public String toString() {
        return "RebuildPasswordDTO{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", newPassword='" + newPassword + '\'' +
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
