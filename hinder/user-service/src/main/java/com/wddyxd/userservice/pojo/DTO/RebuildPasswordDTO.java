package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import com.wddyxd.userservice.pojo.DTO.update.BaseUserUpdateDTO;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 根据验证码找回密码接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 12:34
 **/

public class RebuildPasswordDTO extends BaseUserUpdateDTO {
    @NotBlank(message = "用户名不能为空", groups = {UpdateGroup.class})
    private String username;
    @NotBlank(message = "手机号不能为空", groups = {UpdateGroup.class})
    private String phone;
    @NotBlank(message = "手机验证码不能为空", groups = {UpdateGroup.class})
    private String phoneCode;
    @NotBlank(message = "新密码不能为空", groups = {UpdateGroup.class})
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
