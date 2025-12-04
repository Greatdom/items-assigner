package com.wddyxd.common.constant;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-27 20:01
 **/

public enum RedisKeyConstant {
    USER_LOGIN_TOKEN("user:login:token:"),
    USER_LOGIN_TOKEN_SET("user:login:token:set:"),
    USER_LOGIN_USERINFO("user:login:userinfo:"),
    USER_LOGIN_PHONE_CODE("user:login:phone:code:"),
    USER_LOGIN_EMAIL_CODE("user:login:email:code:");

    public final String key;

    RedisKeyConstant(String key) {
        this.key = key;
    }
}
