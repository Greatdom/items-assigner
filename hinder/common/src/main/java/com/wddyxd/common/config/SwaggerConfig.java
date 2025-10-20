package com.wddyxd.common.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: items-assigner
 * @description: 配置了接口文档的Bean用于测试接口
 * @author: wddyxd
 * @create: 2025-10-20 20:00
 **/

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("计划经济分配机")
                        .description("计划经济分配机")
                        .contact(new Contact().name("wddyxd").email("www778005739@outlook.com").url("127.0.0.1"))
                        .version("1.0.0"));
    }
}