package com.wddyxd.security.exception;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.utils.ResponseUtil;
import com.wddyxd.common.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-08 13:44
 **/

@Component
public class CustomAccessDeniedExceptionHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        if(accessDeniedException instanceof SecurityAccessException){
            Integer code = ((SecurityAccessException) accessDeniedException).getCode();
            String msg = ((SecurityAccessException) accessDeniedException).getMsg();
            ResponseUtil.out(response, Result.error(code,msg));
        }else{
            Result<Object> result = Result.error(ResultCodeEnum.ACCESS_DENIED_ERROR);
            ResponseUtil.out(response, result);
        }
    }
}
