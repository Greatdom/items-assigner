package com.wddyxd.common.constant;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-04 17:23
 **/

public class CommonConstant {

    public static final Long LOGIN_TOKEN_EXPIRATION = 86400000L;//token过期时间，单位毫秒，默认1天
    public static final String LOGIN_TOKEN_SIGN_KEY = "123456";//token密钥
    public static final Integer MAX_LOGIN_TOKEN_PER_USER = 3;

    public static final Integer REDIS_USER_LOGIN_TOKEN_EXPIRE_MINUTES = 60;
    public static final Integer REDIS_USER_LOGIN_USERINFO_EXPIRE_DAYS = 7;


}
