package com.wddyxd.common.constant;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-27 20:01
 **/

public enum RedisKeyConstants {
    USER_LOGIN_TOKEN("user:login:token:"),
    USER_LOGIN_USERINFO("user:login:userinfo:");

    public final String key;

    RedisKeyConstants(String key) {
        this.key = key;
    }
}
