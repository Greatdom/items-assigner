package com.wddyxd.userservice.controller;


import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.Role;
import com.wddyxd.userservice.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: items-assigner
 * @description: 角色接口控制器
 * @author: wddyxd
 * @create: 2025-10-20 21:05
 **/

@RestController
@RequestMapping("/user/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    public Result<List<Role>> list(){
        return Result.success(roleService.list());
    }


    //根据用户获取角色数据
    @GetMapping("/toAssign/{userId}")
    public Result<Map<String, Object>> toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return Result.success(roleMap);
    }


    //CRUD
    @PostMapping("/add")
    public Result<Role> add(@RequestBody Role role){
        roleService.save(role);
        return Result.success();
    }

}
