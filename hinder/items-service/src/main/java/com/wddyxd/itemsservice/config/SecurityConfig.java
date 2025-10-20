package com.wddyxd.itemsservice.config;


import com.wddyxd.security.config.TokenWebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @program: 微服务脚手架
 * @description: 引用security模块的总配置类
 * @author: wddyxd
 * @create: 2025-10-14 11:58
 **/
@Configuration
@Import(TokenWebSecurityConfig.class)
public class SecurityConfig {
}