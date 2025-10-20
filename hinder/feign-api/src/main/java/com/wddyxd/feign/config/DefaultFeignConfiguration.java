package com.wddyxd.feign.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @program: items-assigner
 * @description: 远程调用配置类
 * @author: wddyxd
 * @create: 2025-10-20 21:30
 **/

public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level logLevel(){
        return Logger.Level.BASIC;
    }
}