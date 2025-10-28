package com.wddyxd.security.service;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.UserClient;
import com.wddyxd.security.pojo.CurrentUserInfo;
import com.wddyxd.security.pojo.LoginUserForm;
import com.wddyxd.security.pojo.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-28 20:56
 **/
@Service("passwordUserDetailsService")
public class PasswordUserDetailsService implements UserDetailsService {

    @Autowired
    private UserClient userClient;


    @Override
    public UserDetails loadUserByUsername(String username){
        com.wddyxd.feign.pojo.securityPojo.SecurityUserDTO getUser;
        Result<com.wddyxd.feign.pojo.securityPojo.SecurityUserDTO> get = userClient.passwordSecurityGetter(username);
        getUser = get.getData();
        if(getUser == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        LoginUserForm loginUserForm = new LoginUserForm();
        BeanUtils.copyProperties(getUser.getLoginUserForm(),loginUserForm);
        CurrentUserInfo currentUserInfo = new CurrentUserInfo();
        BeanUtils.copyProperties(getUser.getCurrentUserInfo(),currentUserInfo);
        return new SecurityUser(loginUserForm,currentUserInfo);
    }

}
