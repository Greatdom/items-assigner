package com.wddyxd.feign.config;


import com.wddyxd.feign.clients.fallback.UserClientFallbackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: items-assigner
 * @description: 远程调用配置类
 * @author: wddyxd
 * @create: 2025-10-20 21:30
 **/

@Configuration
public class DefaultFeignConfiguration {
    @Bean
    public UserClientFallbackFactory userClientFallbackFactory(){
        return new UserClientFallbackFactory();
    }

}