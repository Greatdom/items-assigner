package com.wddyxd.userservice.controller;


import com.wddyxd.userservice.pojo.DTO.CustomUserRegisterDTO;
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
public class AuthServiceTests {

    @Autowired
    private IAuthService authService;

    @Test
    public void passwordSecurityGetter() {
        System.out.println(authService.passwordSecurityGetter("wddyxd"));
    }

    @Test
    public void phoneCodeSecurityGetter() {
        System.out.println(authService.phoneCodeSecurityGetter("18888888888"));
    }

    @Test
    public void emailCodeSecurityGetter() {
        System.out.println(authService.emailCodeSecurityGetter("<EMAIL>"));
    }

    @Test
    public void phoneCode() {
        authService.phoneCode("18888888888");
    }

    @Test
    public void emailCode() {
        authService.emailCode("<EMAIL>");
    }

    @Test
    public void customUserRegister(){
        CustomUserRegisterDTO customUserRegisterDTO = new CustomUserRegisterDTO();
        authService.customUserRegister(customUserRegisterDTO);
    }

    @Test
    public void merchantRegister(){
        CustomUserRegisterDTO customUserRegisterDTO = new CustomUserRegisterDTO();
        authService.merchantRegister(customUserRegisterDTO);
    }

    @Test
    public void rebuildPassword(){
        CustomUserRegisterDTO customUserRegisterDTO = new CustomUserRegisterDTO();
        authService.rebuildPassword(customUserRegisterDTO);
    }

}
