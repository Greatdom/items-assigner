package com.wddyxd.userservice.pojo.VO;


import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;

/**
 * @program: items-assigner
 * @description: 用户登录接口的邮箱验证码登录接口的结果包装类,包装邮箱验证码和用户信息
 * @author: wddyxd
 * @create: 2025-12-01 10:42
 **/
@Deprecated
public class EmailCodeSecurityGetterVO {

    private String emailCode;
    private CurrentUserDTO currentUserDTO;

    @Override
    public String toString() {
        return "EmailCodeSecurityGetterVO{" +
                "emailCode='" + emailCode + '\'' +
                ", currentUserDTO=" + currentUserDTO +
                '}';
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public CurrentUserDTO getCurrentUserDTO() {
        return currentUserDTO;
    }

    public void setCurrentUserDTO(CurrentUserDTO currentUserDTO) {
        this.currentUserDTO = currentUserDTO;
    }
}
