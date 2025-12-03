package com.wddyxd.common.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 17:35
 **/

/**
 * 公共 RestTemplate 自动配置类
 * 这是个被feign代替的废弃类
 */
@Configuration
public class RestTemplateAutoConfiguration {

    /**
     * 定义公共 RestTemplate Bean
     * @ConditionalOnMissingBean：微服务可自定义同名 Bean 覆盖此配置（灵活性）
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 添加 token 透传拦截器（与原逻辑一致，抽取到公共模块）
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new TokenTransmitInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    /**
     * 抽取 token 透传拦截器为独立类（更易维护、可复用）
     */
    public static class TokenTransmitInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            // 从当前请求上下文获取 token 并透传
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                String token = attributes.getRequest().getHeader("token");
                if (token != null && !token.isEmpty()) {
                    request.getHeaders().add("token", token);
                }
            }
            return execution.execute(request, body);
        }
    }
}