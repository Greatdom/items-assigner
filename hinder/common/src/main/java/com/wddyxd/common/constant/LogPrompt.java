package com.wddyxd.common.constant;

public enum LogPrompt {

    //ERROR
    FEIGN_ERROR("feign调用错误"),
    IOEXCEPTION_ERROR("IO异常"),
    PASSWORD_ENCODE_ERROR("密码编码错误"),
    PARAM_EMPTY_ERROR("不该为空的参数为空的错误"),
    REDIS_SERVER_ERROR("Redis异常"),
    REDIS_GET_DATA_ERROR("Redis获取数据异常"),
    TOKEN_EXPIRED_INFO("Token已过期"),
    TOKEN_FORMAT_ERROR("非预期格式的Token"),
    //WARN
    DELETE_UNEXIST_INFO("试图删除不存在的记录"),

    //INFO
    SUCCESS_INFO("操作成功");

    public final String msg;

    LogPrompt(String msg) {
        this.msg = msg;
    }
}
