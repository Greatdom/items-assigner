package com.wddyxd.userservice.controller;


import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
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
        System.out.println(roleService.detail(1996956488421613570L));
    }

    @Test
    public void add() {
        roleService.add("ROLE_SUPER_ADMIN",2);
        roleService.add("ROLE_CUSTOM_ADMIN",2);
        roleService.add("ROLE_NEW_ADMIN",2);
        roleService.add("ROLE_CUSTOM_MERCHANT",1);
        roleService.add("ROLE_NEW_MERCHANT",1);
        roleService.add("ROLE_CUSTOM_USER",0);
        roleService.add("ROLE_NEW_USER",0);

    }

    @Test
    public void assign() {
        roleService.assign(1996953140859625474L, new Long[]{1996956488421613570L});
    }

    @Test
    public void list() {
        System.out.println(Result.success(roleService.List(new SearchDTO())));
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
