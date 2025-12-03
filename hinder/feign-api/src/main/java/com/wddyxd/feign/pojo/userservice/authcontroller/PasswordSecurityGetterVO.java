package com.wddyxd.feign.pojo.userservice.authcontroller;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 16:52
 **/

public class PasswordSecurityGetterVO {

    private String password;
    private CurrentUserDTO currentUserDTO;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CurrentUserDTO getCurrentUserDTO() {
        return currentUserDTO;
    }

    public void setCurrentUserDTO(CurrentUserDTO currentUserDTO) {
        this.currentUserDTO = currentUserDTO;
    }

}
