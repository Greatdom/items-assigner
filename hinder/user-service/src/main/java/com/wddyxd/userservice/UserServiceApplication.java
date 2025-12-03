package com.wddyxd.userservice;

import com.wddyxd.feign.clients.userservice.AuthClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan("com.wddyxd")
@MapperScan("com.wddyxd.userservice.mapper")
@EnableFeignClients(clients={AuthClient.class})
@EnableAspectJAutoProxy(exposeProxy = true)
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
