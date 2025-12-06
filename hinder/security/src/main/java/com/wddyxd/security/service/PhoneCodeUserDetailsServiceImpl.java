package com.wddyxd.security.service;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.feign.clients.userservice.AuthClient;
import com.wddyxd.security.exception.SecurityAuthException;
import com.wddyxd.security.pojo.CurrentUserDTO;
import com.wddyxd.security.pojo.LoginUserForm;
import com.wddyxd.security.pojo.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: spring-security
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-28 18:34
 **/

@Service("phoneCodeUserDetailsService")
public class PhoneCodeUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthClient authClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.wddyxd.feign.pojo.userservice.authcontroller.PhoneCodeSecurityGetterVO get;
        get = authClient.phoneCodeSecurityGetter(username).getData();
        if(get== null||get.getPhoneCode()==null||get.getCurrentUserDTO()== null){
            throw new SecurityAuthException(ResultCodeEnum.USER_OR_PASSWORD_ERROR);
        }
        LoginUserForm loginUserForm = new LoginUserForm();
        loginUserForm.setPhoneCode(get.getPhoneCode());
        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        BeanUtils.copyProperties(get.getCurrentUserDTO(),currentUserDTO);
        return new SecurityUser(loginUserForm,currentUserDTO);
    }
}