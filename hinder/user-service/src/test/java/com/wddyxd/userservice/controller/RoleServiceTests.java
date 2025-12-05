package com.wddyxd.userservice.controller;


import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.pojo.entity.Role;
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

    @Test
    public void add() {
        roleService.add("ROLE_TEST");
    }

    @Test
    public void list() {
        System.out.println(roleService.List(new SearchDTO()));
    }

    @Test
    public void update() {
        Role role = new Role();
        role.setId(-1L);
        role.setName("ROLE_TEST_UPDATED");
        roleService.update(role);
    }

    @Test
    public void delete() {
        roleService.delete(-1L);
    }

}
