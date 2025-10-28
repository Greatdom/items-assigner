package com.wddyxd.userservice.controller;


import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.User;
import com.wddyxd.userservice.pojo.securityDTO.SecurityUserDTO;
import com.wddyxd.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-28 23:36
 **/

@RestController
@RequestMapping("/user/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){return null;}

    @PostMapping("/logout")
    public Result<User> logout(){
        return null;
    }

    @GetMapping("/passwordSecurityGetter/{username}")
    public Result<SecurityUserDTO> passwordSecurityGetter(@PathVariable String username){
        return Result.success(userService.passwordSecurityGetter(username));
    }

}
