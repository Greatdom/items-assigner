package com.wddyxd.userservice.controller;


import com.wddyxd.common.utils.MD5Encoder;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.User;
import com.wddyxd.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: items-assigner
 * @description: 用户接口控制器
 * @author: wddyxd
 * @create: 2025-10-20 20:52
 **/

@RestController
@RequestMapping("/user/user")
@Tag(name = "用户控制器", description = "用户管理相关接口")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        return userService.login(user);
    }
    @PostMapping("/assignRole")
    public Result<User> assignRole(@RequestParam Long userId, @RequestParam Long[] roleIds){
        //TODO 分配角色
        userService.assignRole(userId,roleIds);
        return Result.success();
    }
    @GetMapping("/me")
    public Result<User> me(){
        //TODO 获取当前登录用户
        return null;
    }
    @PostMapping("/logout")
    public Result<User> logout(){
        return Result.success();
    }





    //CRUD 操作
    @GetMapping("/get/{id}")
    public Result<User> get(@PathVariable Long id){
        return Result.success(userService.getById(id));
    }
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user.list')")
    @Operation(summary = "获取用户列表", description = "根据条件获取用户列表")
    public Result<List<User>> selectAll(){
        return Result.success(userService.list());
    }

    //添加用户
    @PostMapping("/add")
    public Result<User> add(@RequestBody User user){
        user.setPassword(MD5Encoder.encrypt(user.getPassword()));
        userService.save(user);
        return Result.success();
    }

    //更改用户信息
    @PutMapping("/update")
    public Result<User> update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result<User> delete(@PathVariable Long id){
        //TODO 删除用户
        return Result.success();
    }
}
