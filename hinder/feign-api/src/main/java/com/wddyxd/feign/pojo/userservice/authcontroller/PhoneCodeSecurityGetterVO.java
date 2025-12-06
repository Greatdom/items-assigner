package com.wddyxd.feign.pojo.userservice.authcontroller;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 14:43
 **/

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
