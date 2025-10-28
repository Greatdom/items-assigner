package com.wddyxd.userservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.utils.MD5Encoder;
import com.wddyxd.common.utils.Result;
import com.wddyxd.security.pojo.SecurityUser;
import com.wddyxd.userservice.pojo.User;
import com.wddyxd.userservice.pojo.dto.CurrentUserDTO;
import com.wddyxd.userservice.pojo.securityDTO.SecurityUserDTO;
import com.wddyxd.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){return null;}
    @GetMapping("/me")
    public Result<CurrentUserDTO> me(){return Result.success(userService.me());}
    @PostMapping("/logout")
    public Result<User> logout(){
        return null;
    }

    @GetMapping("/passwordSecurityGetter/{username}")
    public Result<SecurityUserDTO> passwordSecurityGetter(@PathVariable String username){
        return Result.success(userService.passwordSecurityGetter(username));
    }





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
        user.setPassword(MD5Encoder.encrypt(user.getPassword()));
        //TODO要自定义方法判断去重
        //要添加分配角色业务
        userService.save(user);
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
    @PreAuthorize("hasAuthority('user.delete')")
    public Result<User> delete(@PathVariable Long id){
        //TODO 删除用户
        return Result.error(ResultCodeEnum.FUNCTION_ERROR);
    }
}
