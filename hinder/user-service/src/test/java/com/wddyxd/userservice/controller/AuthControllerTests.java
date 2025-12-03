package com.wddyxd.userservice.controller;


import com.wddyxd.userservice.service.Interface.IAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 16:46
 **/
@SpringBootTest
public class AuthControllerTests {

    @Autowired
    private AuthController authController;

    @Autowired
    private IAuthService authService;

    @Test
    public void passwordSecurityGetter() {
        System.out.println(authService.passwordSecurityGetter("wddyxd"));
    }

}
