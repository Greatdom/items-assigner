package com.wddyxd.userservice.controller;


import com.wddyxd.userservice.service.Interface.IRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-04 20:23
 **/
@SpringBootTest
public class RoleServiceTests {

    @Autowired
    private IRoleService roleService;

    @Test
    public void detail() {
        System.out.println(roleService.detail(1984518164557385728L));
    }

}
