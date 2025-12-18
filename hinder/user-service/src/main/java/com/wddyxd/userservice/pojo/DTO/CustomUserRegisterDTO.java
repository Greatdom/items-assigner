package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

/**
 * @program: items-assigner
 * @description: 用户端用户注册接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 12:21
 **/

public class CustomUserRegisterDTO {
    @Null(message = "用户ID应为空", groups = {AddGroup.class})
    private Long userId;
    @NotBlank(message = "SKU规格不能为空", groups = {AddGroup.class})
    private String username;
    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class})
    private String phone;
    @NotBlank(message = "手机验证码不能为空", groups = {AddGroup.class})
    private String phoneCode;
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class})
    private String email;
    @NotBlank(message = "邮箱验证码不能为空", groups = {AddGroup.class})
    private String emailCode;
    @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
    private String password;

    @Override
    public String toString() {
        return "CustomUserRegisterDTO{" +
                "userId=" + userId +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", email='" + email + '\'' +
                ", emailCode='" + emailCode + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
