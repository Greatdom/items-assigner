package com.wddyxd.feign.clients.userservice;


import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.AuthClientFallbackFactory;
import com.wddyxd.feign.pojo.userservice.authcontroller.PasswordSecurityGetterVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 16:48
 **/
@FeignClient(value = "user-service",fallbackFactory = AuthClientFallbackFactory.class)
public interface AuthClient {

    @GetMapping("/user/auth/passwordSecurityGetter/{username}")
    Result<PasswordSecurityGetterVO> passwordSecurityGetter(@PathVariable String username);

}
