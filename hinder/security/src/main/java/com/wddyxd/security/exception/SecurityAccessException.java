package com.wddyxd.security.exception;

import com.wddyxd.common.constant.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;

public class SecurityAccessException  extends AccessDeniedException {
    private Integer code;
    private String msg;
    public SecurityAccessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.msg);
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

    public SecurityAccessException(Integer code, String msg) {
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
