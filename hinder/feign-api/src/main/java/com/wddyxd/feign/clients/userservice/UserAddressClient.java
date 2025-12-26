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

    @GetMapping("/user/userAddress/get/{id}")
    public Result<UserAddress> get(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id);

}
