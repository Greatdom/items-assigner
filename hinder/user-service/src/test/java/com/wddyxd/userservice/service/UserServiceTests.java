package com.wddyxd.userservice.service;


import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.pojo.DTO.CustomUserRegisterDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdateEmailDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdatePasswordDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdatePhoneDTO;
import com.wddyxd.userservice.service.Interface.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 22:02
 **/

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    @Test
    public void List(){
        System.out.println(userService.List(new SearchDTO()));
    }

    @Test
    public void detail(){
        System.out.println(userService.detail(1L));
    }

    @Test
    public void profile(){
        System.out.println(userService.profile(1L));
    }

    @Test
    public void me(){
        System.out.println("userService.me()");
    }

    @Test
    public void visit(){
        System.out.println(userService.visit(1L));
    }

    @Test
    public void addAdmin(){
        CustomUserRegisterDTO customUserRegisterDTO = new CustomUserRegisterDTO();
        customUserRegisterDTO.setUsername("ADMIN");
        customUserRegisterDTO.setPassword("123456");
        customUserRegisterDTO.setPhone("15626448742");
        customUserRegisterDTO.setEmail("www778005729@outlook.com");
        userService.addAdmin(customUserRegisterDTO);
    }

    @Test
    public void updatePassword(){
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        userService.updatePassword(updatePasswordDTO);
    }

    @Test
    public void updatePhone(){
        UpdatePhoneDTO updatePhoneDTO = new UpdatePhoneDTO();
        userService.updatePhone(updatePhoneDTO);
    }

    @Test
    public void updateEmail(){
        UpdateEmailDTO updateEmailDTO = new UpdateEmailDTO();
        userService.updateEmail(updateEmailDTO);
    }

}
