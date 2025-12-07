package com.wddyxd.security.security;


import com.wddyxd.common.utils.encoder.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: spring_security内置密码匹配接口的自定义实现
 * @author: wddyxd
 * @create: 2025-10-20 20:26
 **/

@Component
public class SecurityPasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SecurityPasswordEncoder() {
        this(-1);
    }
    public SecurityPasswordEncoder(int strength) {
    }
    //进行MD5加密
    @Override
    public String encode(CharSequence charSequence) {
        return passwordEncoder.encode(charSequence.toString());
    }
    //进行密码比对
    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String inputEncrypted = passwordEncoder.encode(charSequence.toString());
        return encodedPassword.equals(inputEncrypted);
    }
}
