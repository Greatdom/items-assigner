package com.wddyxd.common.exceptionhandler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.utils.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: items-assigner
 * @description: 全局异常捕捉处理器
 * @author: wddyxd
 * @create: 2025-10-20 20:03
 **/

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理一般异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<?> error(CustomException e){
        return Result.error(e.getCode(),e.getMsg());
    }

    /**
     * 处理@RequestBody参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.error(HttpStatus.BAD_REQUEST.value(), "参数校验失败：" + errorMsg);
    }

    /**
     * 处理@RequestParam/@PathVariable参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<?> handleConstraintViolation(ConstraintViolationException e) {
        String errorMsg = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));
        return Result.error(HttpStatus.BAD_REQUEST.value(), "参数校验失败：" + errorMsg);
    }

    /**
     * 处理表单提交参数校验异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<?> handleBindException(BindException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.error(HttpStatus.BAD_REQUEST.value(), "参数校验失败：" + errorMsg);
    }

}
