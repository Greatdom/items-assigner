package com.wddyxd.userservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.dto.CurrentUserDTO;
import com.wddyxd.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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


    @GetMapping("/me")
    public Result<CurrentUserDTO> me(){return Result.success(userService.me());}







    //CRUD 操作
    @GetMapping("/get/{id}")

    public Result<User> get(@PathVariable Long id){
        return Result.success(userService.getById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user.list')")
    @Operation(summary = "获取用户列表", description = "根据条件获取用户列表")
    public Result<?> selectAll(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search){
        return userService.selectAll(pageNum, pageSize, search);
    }




    //添加用户
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user.add')")
    public Result<User> add(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }

    //更改用户信息
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('user.update')")
    public Result<User> update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('user.remove')")
    public Result<User> delete(@PathVariable Long id){
        //TODO 删除用户
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
}
