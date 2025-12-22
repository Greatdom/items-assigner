package com.wddyxd.fileservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 23:59
 **/

@SpringBootApplication
@ComponentScan("com.wddyxd")
@MapperScan("com.wddyxd.fileservice.mapper")
@EnableFeignClients(basePackages = "com.wddyxd.feign.clients")
@EnableAspectJAutoProxy(exposeProxy = true)
public class FileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
