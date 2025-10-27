package com.wddyxd.common.exceptionhandler;


import com.wddyxd.common.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: items-assigner
 * @description: 全局异常捕捉处理器
 * @author: wddyxd
 * @create: 2025-10-20 20:03
 **/

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<?> error(CustomException e){
        e.printStackTrace();
        return Result.error(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> error(Exception e){
        e.printStackTrace();
        return Result.error();
    }


}
