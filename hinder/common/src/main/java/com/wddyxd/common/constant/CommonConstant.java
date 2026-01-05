package com.wddyxd.common.constant;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-04 17:23
 **/

public class CommonConstant {
    //auth
    public static final Long LOGIN_TOKEN_EXPIRATION = 86400000L;//token过期时间，单位毫秒，默认1天
    public static final String LOGIN_TOKEN_SIGN_KEY = "123456";//token密钥
    public static final Integer MAX_LOGIN_TOKEN_PER_USER = 3;//一个用户最多持有的token数量,用于控制多端登录数量
    //user
    public static final Integer REDIS_USER_LOGIN_TOKEN_EXPIRE_MINUTES = 60;//用户登录token过期时间，单位分钟
    public static final Integer REDIS_USER_LOGIN_USERINFO_EXPIRE_DAYS = 7;//用户登录用户信息过期时间，单位天
    public static final Integer REDIS_USER_LOGIN_PHONE_CODE_EXPIRE_MINUTES = 15;//手机验证码过期时间，单位分钟
    public static final Integer REDIS_USER_LOGIN_EMAIL_CODE_EXPIRE_MINUTES = 15;//邮箱验证码过期时间，单位分钟
    //role
    public static final Integer ROLE_GROUP_NUM = 3;//角色组数量
    //product
    public static final Integer MAX_PRODUCT_SKU_NUM = 20;//商品最大SKU数量
    //file-service
    public static final long MAX_FILE_SIZE = 1024 * 1024 * 10L;//最大传输文件大小，10M
    public static final String[] IMAGE_TYPES = {"jpg", "jpeg", "png", "webp", "bmp"};//图片文件类型
    public static final String FILE_COMPRESS_QUEUE="file-compress-queue";//RabbitMQ压缩队列
    public static final String FILE_DELETE_QUEUE="file-delete-queue";//RabbitMQ删除队列
    public static final String FILE_STORAGE_PATH="E:/FILE_STORAGE/";//文件存储路径
    public static final float COMPRESS_QUALITY = 0.5f; // 图片压缩质量（0-1，数值越小压缩率越高，失真越严重）
    //alipay
    public static final String ALIPAY_APP_ID = "2021XXXXXX695127";
    public static final String ALIPAY_PRIVATE_KEY = "XXXXXX";
    public static final String ALIPAY_PUBLIC_KEY = "XXXXXX";
    public static final String ALIPAY_NOTIFY_URL = "http://dfd46925.naXXXXXXee.cc/api/pay/alipay/notify";
    public static final String ALIPAY_RETURN_URL = "http://dfd46925.naXXXXXXee.cc/api/pay/alipay/return";
    public static final String ALIPAY_GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    public static final String SIGN_TYPE = "RSA2";
    public static final String CHARSET = "UTF-8";
    //order.add
    public static final String ORDER_ADD_QUEUE = "order-add-queue";
}
