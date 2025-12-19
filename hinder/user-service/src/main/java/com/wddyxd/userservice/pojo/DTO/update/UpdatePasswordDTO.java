package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 更新用户密码接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:33
 **/

public class UpdatePasswordDTO extends BaseUserUpdateDTO{

    @NotBlank(message = "旧密码不能为空", groups = {UpdateGroup.class})
    private String oldPassword;
    @NotBlank(message = "新密码不能为空", groups = {UpdateGroup.class})
    private String newPassword;
    @NotBlank(message = "手机验证码不能为空", groups = {UpdateGroup.class})
    private String phoneCode;
    @NotBlank(message = "手机号不能为空", groups = {UpdateGroup.class})
    private String phone;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
