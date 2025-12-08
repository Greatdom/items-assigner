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
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-02 14:26
 **/
@Service("emailCodeUserDetailsService")
public class EmailCodeUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthClient authClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.wddyxd.feign.pojo.userservice.authcontroller.CurrentUserDTO get;
        get = authClient.emailCodeSecurityGetter(username).getData();
        if(get== null){
            throw new SecurityAuthException(ResultCodeEnum.USER_OR_PASSWORD_ERROR);
        }
        LoginUserForm loginUserForm = new LoginUserForm();
        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        BeanUtils.copyProperties(get,currentUserDTO);
        return new SecurityUser(loginUserForm,currentUserDTO);
    }
}
