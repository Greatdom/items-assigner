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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: items-assigner
 * @description: 当用户没有认证授权时请求被拦截的类
 * @author: wddyxd
 * @create: 2025-10-20 20:30
 **/

@Component
public class CustomUnAuthExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, Result.error(ResultCodeEnum.UN_LOGIN_ERROR));

        if(e instanceof SecurityAuthException){
            Integer code = ((SecurityAuthException) e).getCode();
            String msg = ((SecurityAuthException) e).getMsg();
            Result<Object> result = Result.error(code,msg);
            ResponseUtil.out(httpServletResponse,result);
        }else{
            Integer code = 401;
            String msg = "尚未认证: " + e.getMessage();
            Result<Object> result = Result.error(code,msg);
            ResponseUtil.out(httpServletResponse,result);
        }

    }
}
