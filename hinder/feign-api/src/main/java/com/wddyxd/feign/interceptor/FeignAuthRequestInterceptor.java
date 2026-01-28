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


    // 定义一个ThreadLocal存储MQ消费者的token
    private static final ThreadLocal<String> MQ_TOKEN_HOLDER = new ThreadLocal<>();

    /**
     * 供MQ消费者设置token的方法
     */
    public static void setMqToken(String token) {
        MQ_TOKEN_HOLDER.set(token);
    }

    /**
     * 清除当前线程的MQ token（防止内存泄漏）
     */
    public static void clearMqToken() {
        MQ_TOKEN_HOLDER.remove();
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = null;
        // 1. 优先从HTTP请求上下文获取token（正常接口调用场景）
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            token = request.getHeader("token");
        }

        // 2. 如果HTTP上下文没有，从MQ的ThreadLocal获取（消费者场景）
        if (token == null) {
            token = MQ_TOKEN_HOLDER.get();
        }

        // 3. 设置token到Feign请求头
        if (token != null) {
            template.header("token", token);
        }
    }

}