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
    public static final Integer MAX_LOGIN_TOKEN_PER_USER = 3;//一个用户最多持有的token数量,用于控制多端登录数量

    public static final Integer REDIS_USER_LOGIN_TOKEN_EXPIRE_MINUTES = 60;//用户登录token过期时间，单位分钟
    public static final Integer REDIS_USER_LOGIN_USERINFO_EXPIRE_DAYS = 7;//用户登录用户信息过期时间，单位天
    public static final Integer REDIS_USER_LOGIN_PHONE_CODE_EXPIRE_MINUTES = 15;//手机验证码过期时间，单位分钟
    public static final Integer REDIS_USER_LOGIN_EMAIL_CODE_EXPIRE_MINUTES = 15;//邮箱验证码过期时间，单位分钟

    public static final Integer ROLE_GROUP_NUM = 3;//角色组数量

    public static final Integer MAX_PRODUCT_SKU_NUM = 20;//商品最大SKU数量

    public static final String filePath = System.getProperty("user.dir") + "/files/";
    //TODO 在不同环境怎么配置不同路径?
    public static final String serverDomain = "http://localhost:10010/api";

}
