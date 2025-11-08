package com.wddyxd.security.exception;

import com.wddyxd.common.constant.ResultCodeEnum;
import org.springframework.security.core.AuthenticationException;

public class SecurityAuthException extends AuthenticationException {

    private Integer code;
    private String msg;
    public SecurityAuthException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.msg);
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

    public SecurityAuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}
