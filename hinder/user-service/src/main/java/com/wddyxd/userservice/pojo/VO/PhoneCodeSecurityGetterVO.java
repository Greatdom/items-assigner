package com.wddyxd.userservice.pojo.VO;


import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;

/**
 * @program: items-assigner
 * @description: 用户登录接口的手机验证码登录接口的结果包装类,包装手机验证码和用户信息
 * @author: wddyxd
 * @create: 2025-12-01 10:36
 **/
@Deprecated
public class PhoneCodeSecurityGetterVO {

    private String phoneCode;
    private CurrentUserDTO currentUserDTO;

    @Override
    public String toString() {
        return "PhoneCodeSecurityGetterVO{" +
                "phoneCode='" + phoneCode + '\'' +
                ", currentUserDTO=" + currentUserDTO +
                '}';
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public CurrentUserDTO getCurrentUserDTO() {
        return currentUserDTO;
    }

    public void setCurrentUserDTO(CurrentUserDTO currentUserDTO) {
        this.currentUserDTO = currentUserDTO;
    }
}
