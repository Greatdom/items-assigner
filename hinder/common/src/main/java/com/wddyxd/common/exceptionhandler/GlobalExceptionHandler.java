package com.wddyxd.common.exceptionhandler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.utils.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @Order(3)
    @ResponseBody
    public Result<?> error(CustomException e){
        return Result.error(e.getCode(),e.getMsg());
    }

// ====================== MySQL/SQL 相关异常 ======================
    /**
     * 处理SQL语法错误（最常见的MySQL异常，如字段名错误、逗号多余等）
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    @Order(1)
    public Result<?> handleBadSqlGrammar(BadSqlGrammarException e) {
        String errorMsg = "SQL语法错误：" + e.getMostSpecificCause().getMessage();
        // 打印详细堆栈（便于排查），但返回给前端简化信息
        e.printStackTrace();
        return Result.error(ResultCodeEnum.SERVER_ERROR.getCode(), errorMsg);
    }
    /**
     * 处理数据库连接失败（如MySQL服务宕机、账号密码错误）
     */
    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    @ResponseBody
    @Order(1)
    public Result<?> handleJdbcConnectionFailure(CannotGetJdbcConnectionException e) {
        String errorMsg = "数据库连接失败：" + e.getMessage();
        e.printStackTrace();
        return Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), errorMsg);
    }
    /**
     * 处理SQL更新行数异常（如更新时主键不存在、影响行数不匹配）
     */
    @ExceptionHandler(JdbcUpdateAffectedIncorrectNumberOfRowsException.class)
    @ResponseBody
    @Order(1)
    public Result<?> handleJdbcUpdateRowError(JdbcUpdateAffectedIncorrectNumberOfRowsException e) {
        String errorMsg = "SQL更新行数异常：" + e.getMessage();
        return Result.error(ResultCodeEnum.SERVER_ERROR.getCode(), errorMsg);
    }
    /**
     * 处理数据库主键/唯一索引重复（如重复插入同一条数据）
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @Order(1)
    @ResponseBody
    public Result<?> handleDuplicateKey(DuplicateKeyException e) {
        String errorMsg = "数据重复：" + e.getMostSpecificCause().getMessage();
        return Result.error(HttpStatus.CONFLICT.value(), errorMsg);
    }
    /**
     * 处理SQL查询超时（兜底其他数据库查询超时场景）
     */
    @ExceptionHandler(QueryTimeoutException.class)
    @Order(1)
    @ResponseBody
    public Result<?> handleQueryTimeout(QueryTimeoutException e) {
        String errorMsg = "数据库查询超时：" + e.getMessage();
        e.printStackTrace();
        return Result.error(HttpStatus.REQUEST_TIMEOUT.value(), errorMsg);
    }
    /**
     * 处理其他数据库访问异常（作为MySQL异常的兜底，非通用RuntimeException）
     */
    @ExceptionHandler(DataAccessException.class)
    @Order(2)
    @ResponseBody
    public Result<?> handleDataAccessException(DataAccessException e) {
        String errorMsg = "数据库操作异常：" + e.getMostSpecificCause().getMessage();
        e.printStackTrace();
        return Result.error(ResultCodeEnum.SERVER_ERROR.getCode(), errorMsg);
    }

    // ====================== Redis 相关异常 ======================
    /**
     * 处理Redis连接失败（如Redis服务宕机、网络不通、密码错误）
     */
    @ExceptionHandler(RedisConnectionFailureException.class)
    @ResponseBody
    @Order(1)
    public Result<?> handleRedisConnectionFailure(RedisConnectionFailureException e) {
        String errorMsg = "Redis连接失败：" + e.getMessage();
        e.printStackTrace();
        return Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), errorMsg);
    }
    /**
     * 处理Redis系统级异常（如Lua脚本执行错误、序列化失败）
     */
    @ExceptionHandler(RedisSystemException.class)
    @ResponseBody
    @Order(1)
    public Result<?> handleRedisSystemException(RedisSystemException e) {
        String errorMsg = "Redis系统异常：" + e.getRootCause().getMessage();
        e.printStackTrace();
        return Result.error(ResultCodeEnum.SERVER_ERROR.getCode(), errorMsg);
    }


    // ====================== 参数校验 相关异常 ======================
    /**
     * 处理@RequestBody参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @Order(3)
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
    @Order(3)
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
    @Order(3)
    public Result<?> handleBindException(BindException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return Result.error(HttpStatus.BAD_REQUEST.value(), "参数校验失败：" + errorMsg);
    }

    // ====================== 兜底异常（可选，捕获未定义的非通用异常） ======================
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(4)
    public Result<?> handleGlobalException(Exception e) {
        // 排除已定义的异常类型，避免重复捕获
        if (e instanceof CustomException || e instanceof DataAccessException
                || e instanceof ConstraintViolationException || e instanceof BindException
                || e instanceof MethodArgumentNotValidException || e instanceof BlockException) {
            throw new RuntimeException(e); // 交给对应处理器处理
        }
        String errorMsg = "系统未知异常：" + e.getMessage();
        e.printStackTrace();
        return Result.error(ResultCodeEnum.SERVER_ERROR.getCode(), errorMsg);
    }

}
