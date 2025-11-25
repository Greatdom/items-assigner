package com.wddyxd.userservice.controller;


import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.entity.Role;
import com.wddyxd.userservice.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        //TODO 未来做成分页
        return Result.success(roleService.list());
    }


    //TODO 被弃用
    @GetMapping("/getByUser/{userId}")
    public Result<List<Role>> getByUser(@PathVariable String userId) {
        List<Role> roles = roleService.getByUser(userId);
//        Map<String, Object> roleMap = roleService.getByUser(userId);
        return Result.success(roles);
    }

    //角色分配
    @PostMapping("/assignRole")
    public Result<Role> assignRole(@RequestParam Long userId,@RequestParam Long[] roleIds) {
        roleService.assignRole(userId,roleIds);
        return Result.success();
    }

    //CRUD
    @PostMapping("/add")
    public Result<Role> add(@RequestBody Role role){
        roleService.save(role);
        return Result.success();
    }
}
