package com.wddyxd.itemsservice;

import com.wddyxd.feign.clients.userservice.AuthClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.wddyxd")
@MapperScan("com.wddyxd.itemsservice.mapper")
@EnableFeignClients(clients={AuthClient.class})
@EnableWebSecurity
public class ItemsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemsServiceApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 添加拦截器传递认证信息
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                                ClientHttpRequestExecution execution) throws IOException {
                // 从当前请求上下文获取认证信息
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    String token = attributes.getRequest().getHeader("token");
                    if (token != null) {
                        request.getHeaders().add("token", token);
                    }
                }
                return execution.execute(request, body);
            }
        });
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}
