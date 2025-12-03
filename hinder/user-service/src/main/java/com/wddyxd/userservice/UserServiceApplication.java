package com.wddyxd.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan("com.wddyxd")
@MapperScan("com.wddyxd.userservice.mapper")
@EnableFeignClients(basePackages = "com.wddyxd.feign.clients.userservice")
@EnableAspectJAutoProxy(exposeProxy = true)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
