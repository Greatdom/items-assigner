package com.wddyxd.feign.clients.userservice;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.AuthClientFallbackFactory;
import com.wddyxd.feign.fallback.UserClientFallbackFactory;
import com.wddyxd.feign.pojo.userservice.authcontroller.CurrentUserDTO;
import com.wddyxd.feign.pojo.userservice.authcontroller.PasswordSecurityGetterVO;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-13 16:35
 **/
// 正确写法：添加contextId，确保唯一
@FeignClient(
        value = "user-service",
        contextId = "userClient",
        fallbackFactory = UserClientFallbackFactory.class
)
public interface UserClient {

    @GetMapping("/user/user/profile/{id}")
    Result<UserProfileVO> profile(@PathVariable Long id);

    @GetMapping("/user/user/getUsername/{id}")
    Result<String> getUsername(@PathVariable Long id);



}
