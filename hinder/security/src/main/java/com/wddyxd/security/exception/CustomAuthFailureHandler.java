package com.wddyxd.security.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.utils.ResponseUtil;
import com.wddyxd.common.utils.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-08 13:42
 **/

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof SecurityAuthException){
            Integer code = ((SecurityAuthException) exception).getCode();
            String msg = ((SecurityAuthException) exception).getMsg();
            Result<Object> result = Result.error(code,msg);
            ResponseUtil.out(response,result);
        }else{
            Integer code = 401;
            String msg = "认证失败: " + exception.getMessage();
            Result<Object> result = Result.error(code,msg);
            ResponseUtil.out(response,result);
        }
    }
}