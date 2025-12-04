package com.wddyxd.common.constant;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-21 20:35
 **/

public enum ResultCodeEnum {
    //CODE:200:正常响应
    SUCCESS(200, "操作成功"),

    //CODE:400:参数错误
    LOGIN_FORM_ERROR(400, "登录表单格式错误"),
    PARAM_ERROR(400, "参数错误"),


    //401:认证失败或未认证
    UN_LOGIN_ERROR(401, "您尚未登录"),
    AUTH_FAILED_ERROR(401, "认证失败"),
    USER_OR_PASSWORD_ERROR(401, "用户名或密码错误"),
    USER_NOT_EXIST_ERROR(401, "用户不存在"),
    USER_EXIST_ERROR(401, "用户已存在"),
    USER_NOT_ACTIVE_ERROR(401, "用户未激活"),
    TOKEN_EXPIRED_ERROR(401, "已过期的TOKEN"),
    TOKEN_FORMAT_ERROR(401, "非预期格式的TOKEN"),
    TOKEN_BLACK_LIST_ERROR(401, "黑名单中的TOKEN"),
    TOKEN_EMPTY_ERROR(401, "空的TOKEN"),
    USER_INFO_ERROR(401, "得不到用户信息"),

    //429:请求过频繁
    SERVER_BUSY_ERROR(503, "服务器繁忙,请稍后重试"),

    //403:权限不足
    ACCESS_DENIED_ERROR(403, "权限不足"),

    //500:服务器错误
    SERVER_ERROR(500, "服务器错误"),
    FUNCTION_ERROR(500, "功能未完成"),
    IOEXCEPTION_ERROR(500, "IO异常"),
    SERVER_MAINTENANCE_ERROR(500, "服务器正在维护"),



    //1000:未知错误
    UNKNOWN_ERROR(1000, "未知错误"),
    UNDEFINED_ERROR(1000, "尚未定义的错误"),
    ;

    public final Integer code;
    public final String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
