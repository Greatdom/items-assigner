package com.wddyxd.feign.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @program: items-assigner
 * @description: 远程调用拦截器，在拦截的时候获取请求的token并传递给服务提供方
 * @author: wddyxd
 * @create: 2025-10-20 21:33
 **/

@Component
public class FeignAuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");
            if (token != null) {
                template.header("token", token);
            }
        }
    }
}