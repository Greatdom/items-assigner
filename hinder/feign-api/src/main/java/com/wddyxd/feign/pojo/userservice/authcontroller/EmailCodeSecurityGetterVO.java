package com.wddyxd.feign.pojo.userservice.authcontroller;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 14:43
 **/

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
