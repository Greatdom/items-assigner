package com.wddyxd.userservice.pojo.DTO.update;


/**
 * @program: items-assigner
 * @description: 换绑手机号接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:39
 **/

public class UpdatePhoneDTO extends BaseUserUpdateDTO{

    private String oldPhone;

    private String newPhone;

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
