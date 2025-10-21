package com.wddyxd.security.security;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.utils.ResponseUtil;
import com.wddyxd.common.utils.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * @program: items-assigner
 * @description: 当用户没有认证授权时请求被拦截的类
 * @author: wddyxd
 * @create: 2025-10-20 20:30
 **/

public class UnauthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, Result.error(ResultCodeEnum.TOKEN_CHECK_FAILED_ERROR));
    }
}
