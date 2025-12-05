package com.wddyxd.userservice.controller;


import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.service.Interface.IPermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 09:48
 **/
@SpringBootTest
public class PermissionServiceTests {

    @Autowired
    private IPermissionService permissionService;

    @Test
    public void List(){
        permissionService.List(new SearchDTO());
    }

    @Test
    public void assignV1(){

        permissionService.assign(-1L,new Long[]{1L,-2L,3L});
    }

    @Test
    public void add(){
        permissionService.add("test","test");
    }

    @Test
    public void update(){
        permissionService.update(-1L,"test","test");
    }

    @Test
    public void delete(){
        permissionService.delete(-1L);
    }

}
