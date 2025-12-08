package com.wddyxd.userservice.service;


import com.wddyxd.userservice.pojo.DTO.UserAddressDTO;
import com.wddyxd.userservice.service.Interface.IUserAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 13:54
 **/
@SpringBootTest
public class UserAddressTests {

    @Autowired
    private IUserAddressService userAddressService;

    @Test
    public void List(){
        System.out.println(userAddressService.List(-1L));
    }

    @Test
    public void add(){
        UserAddressDTO userAddressDTO = new UserAddressDTO();
        userAddressService.add(userAddressDTO);
    }

    @Test
    public void update(){
        List<UserAddressDTO> userAddressDTOS = new ArrayList<>();
        userAddressService.update(userAddressDTOS);
    }

    @Test
    public void delete(){
        userAddressService.delete(-1L);
    }

}
