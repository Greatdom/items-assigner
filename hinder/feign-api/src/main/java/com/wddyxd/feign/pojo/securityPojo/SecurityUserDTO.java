package com.wddyxd.feign.pojo.securityPojo;



/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-28 22:21
 **/

public class SecurityUserDTO {

    private LoginUserForm loginUserForm;
    private CurrentUserInfo currentUserInfo;

    public LoginUserForm getLoginUserForm() {
        return loginUserForm;
    }

    public SecurityUserDTO(LoginUserForm loginUserForm, CurrentUserInfo currentUserInfo) {
        this.loginUserForm = loginUserForm;
        this.currentUserInfo = currentUserInfo;
    }


    public void setLoginUserForm(LoginUserForm loginUserForm) {
        this.loginUserForm = loginUserForm;
    }

    public CurrentUserInfo getCurrentUserInfo() {
        return currentUserInfo;
    }

    public void setCurrentUserInfo(CurrentUserInfo currentUserInfo) {
        this.currentUserInfo = currentUserInfo;
    }

    @Override
    public String toString() {
        return "SecurityUserDTO{" +
                "loginUserForm=" + loginUserForm +
                ", currentUserInfo=" + currentUserInfo +
                '}';
    }
}
