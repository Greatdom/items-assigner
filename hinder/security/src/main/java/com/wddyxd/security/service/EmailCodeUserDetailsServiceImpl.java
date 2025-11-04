package com.wddyxd.security.service;


import com.wddyxd.common.constant.ResultCodeEnum;
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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("EmailUserDetailsServiceImpl.loadUserByUsername");
        throw new RuntimeException(ResultCodeEnum.FUNCTION_ERROR.msg);
    }
}
