package com.wddyxd.security.service;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.feign.clients.userservice.AuthClient;
import com.wddyxd.security.exception.SecurityAuthException;
import com.wddyxd.security.pojo.CurrentUserDTO;
import com.wddyxd.security.pojo.LoginUserForm;
import com.wddyxd.security.pojo.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PasswordUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthClient authClient;

    private static final Logger log = LoggerFactory.getLogger(PasswordUserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username){
        com.wddyxd.feign.pojo.userservice.authcontroller.PasswordSecurityGetterVO get;
        get = authClient.passwordSecurityGetter(username).getData();
        if(get== null||get.getPassword()==null||get.getCurrentUserDTO()== null){
            throw new SecurityAuthException(ResultCodeEnum.USER_OR_PASSWORD_ERROR);
        }
        LoginUserForm loginUserForm = new LoginUserForm();
        loginUserForm.setPassword(get.getPassword());
        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        BeanUtils.copyProperties(get.getCurrentUserDTO(),currentUserDTO);
        return new SecurityUser(loginUserForm,currentUserDTO);
    }

}
