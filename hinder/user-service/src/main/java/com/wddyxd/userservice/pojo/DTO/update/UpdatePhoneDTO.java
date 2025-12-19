package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 换绑手机号接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:39
 **/

public class UpdatePhoneDTO extends BaseUserUpdateDTO{
    @NotBlank(message = "旧手机号不能为空", groups = {UpdateGroup.class})
    private String oldPhone;
    @NotBlank(message = "新手机号不能为空", groups = {UpdateGroup.class})
    private String newPhone;
    @NotBlank(message = "手机验证码不能为空", groups = {UpdateGroup.class})
    private String phoneCode;

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }
}
