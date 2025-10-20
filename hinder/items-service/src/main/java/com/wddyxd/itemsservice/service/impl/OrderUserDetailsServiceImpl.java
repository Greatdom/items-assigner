package com.wddyxd.itemsservice.service.impl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-14 12:03
 **/

@Service("userDetailsService")
public class OrderUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 由于order-service不需要本地认证，这里可以抛出异常或者返回默认用户
        throw new UsernameNotFoundException("User not found in order service");
    }
}