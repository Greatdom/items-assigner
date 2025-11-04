package com.wddyxd.feign.clients;


import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.pojo.User;
import com.wddyxd.feign.pojo.securityPojo.SecurityUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: items-assigner
 * @description: user模块的user接口控制器的远程调用接口控制器
 * @author: wddyxd
 * @create: 2025-10-20 21:31
 **/

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/user/user/get/{id}")
    Result<User> FindById(@PathVariable("id") Long id);

    @GetMapping("/user/auth/passwordSecurityGetter/{username}")
    Result<SecurityUserDTO> passwordSecurityGetter(@PathVariable String username);
}
