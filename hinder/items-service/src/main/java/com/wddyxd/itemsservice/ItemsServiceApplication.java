package com.wddyxd.itemsservice;

import com.wddyxd.feign.clients.userservice.AuthClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan("com.wddyxd")
@MapperScan("com.wddyxd.itemsservice.mapper")
@EnableFeignClients(clients={AuthClient.class})
@EnableWebSecurity
public class ItemsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemsServiceApplication.class, args);
    }




}
