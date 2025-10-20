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
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Result<?> error(Exception e){
        e.fillInStackTrace();
        return Result.error(e.getMessage());
    }
}
