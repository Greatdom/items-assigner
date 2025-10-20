package com.wddyxd.security.security;


import com.wddyxd.common.utils.MD5Encoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: spring_security内置密码匹配接口的自定义实现
 * @author: wddyxd
 * @create: 2025-10-20 20:26
 **/

@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    public DefaultPasswordEncoder() {
        this(-1);
    }
    public DefaultPasswordEncoder(int strength) {
    }
    //进行MD5加密
    @Override
    public String encode(CharSequence charSequence) {
        return MD5Encoder.encrypt(charSequence.toString());
    }
    //进行密码比对
    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        String inputEncrypted = MD5Encoder.encrypt(charSequence.toString());
        boolean result = encodedPassword.equals(inputEncrypted);
        return result;
    }
}
