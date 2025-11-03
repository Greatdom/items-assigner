package com.wddyxd.common.constant;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-21 20:35
 **/

public enum ResultCodeEnum {
    SUCCESS(200, "操作成功"),

    PARAM_ERROR(400, "参数异常"),
    TOKEN_CHECK_FAILED_ERROR(403, "没有授权"),
    AUTH_FAILED_ERROR(401, "认证失败"),
    USER_NOT_EXIST_ERROR(401, "用户不存在"),
    PASSWORD_ERROR(401, "密码错误"),


    FUNCTION_ERROR(500, "功能未完成"),
    PASSWORD_ENCODE_ERROR(501, "密码编码错误"),
    FEIGN_ERROR(502, "feign调用错误"),

    UNKNOWN_ERROR(1000, "未知错误"),
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
