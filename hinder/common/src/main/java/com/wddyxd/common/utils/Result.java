package com.wddyxd.common.utils;


import com.wddyxd.common.constant.ResultCodeEnum;

/**
 * @program: items-assigner
 * @description: 统一结果返回集
 * @author: wddyxd
 * @create: 2025-10-20 20:08
 **/

public class Result<T> {
    private Integer code;
    private String msg;
    private T data;


    private Result(T data){
        this.data = data;
    }
    private Result(){

    }


     public static <T> Result<T> success(T data){
        Result<T> tResult = new Result<>(data);
        tResult.setCode(ResultCodeEnum.SUCCESS.code);
        tResult.setMsg(ResultCodeEnum.SUCCESS.msg);
        return tResult;
    }

    public static <T> Result<T> success(){
        Result<T> tResult = new Result<>();
        tResult.setCode(ResultCodeEnum.SUCCESS.code);
        tResult.setMsg(ResultCodeEnum.SUCCESS.msg);
        return tResult;
    }

    public static <T> Result<T> error(){
        Result<T> tResult = new Result<>();
        tResult.setCode(ResultCodeEnum.SYSTEM_ERROR.code);
        tResult.setMsg(ResultCodeEnum.SYSTEM_ERROR.msg);
        return tResult;
    }
    public static <T> Result<T> error(Integer code, String msg){
        Result<T> tResult = new Result<>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }

    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum){
        Result<T> tResult = new Result<>();
        tResult.setCode(resultCodeEnum.code);
        tResult.setMsg(resultCodeEnum.msg);
        return tResult;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}