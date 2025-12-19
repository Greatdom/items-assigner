package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 换绑邮箱接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:39
 **/

public class UpdateEmailDTO extends BaseUserUpdateDTO{

    @NotBlank(message = "旧邮箱不能为空", groups = {UpdateGroup.class})
    private String oldEmail;

    @NotBlank(message = "新邮箱不能为空", groups = {UpdateGroup.class})
    private String newEmail;

    @NotBlank(message = "邮箱验证码不能为空", groups = {UpdateGroup.class})
    private String emailCode;

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }
}
