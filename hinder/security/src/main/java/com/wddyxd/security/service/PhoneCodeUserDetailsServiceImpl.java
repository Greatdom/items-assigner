package com.wddyxd.security.service;


import com.wddyxd.common.constant.ResultCodeEnum;
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



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("PhoneUserDetailsServiceImpl.loadUserByUsername");
        throw new RuntimeException(ResultCodeEnum.FUNCTION_ERROR.msg);
    }
}