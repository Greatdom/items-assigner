package com.wddyxd.feign.clients.userservice;


import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.userservice.UserAddressClientFallbackFactory;
import com.wddyxd.feign.fallback.userservice.UserClientFallbackFactory;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserAddress;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-26 20:37
 **/

@FeignClient(
        value = "user-service",
        contextId = "userAddressClient",
        fallbackFactory = UserAddressClientFallbackFactory.class
)
public interface UserAddressClient {

    @GetMapping("/user/userAddress/get/default/{id}")
    public Result<UserAddress> getDefault(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id);

    @GetMapping("/get/special/{id}")
    //访问者的id等于参数的id
    @Operation(summary = "获取个人用户地址簿", description = "获取个人用户地址簿")
    public Result<UserAddress> getSpecial(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id);


}
