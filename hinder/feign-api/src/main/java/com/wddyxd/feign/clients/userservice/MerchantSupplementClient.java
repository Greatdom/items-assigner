package com.wddyxd.feign.clients.userservice;


import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.userservice.MerchantSupplementClientFallbackFactory;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-16 15:55
 **/
@FeignClient(
        value = "user-service",
        contextId = "merchantSupplementClient",
        fallbackFactory = MerchantSupplementClientFallbackFactory.class
)
public interface MerchantSupplementClient {

    @GetMapping("/user/merchantSupplement/getShopName/{id}")
    public Result<String> getShopName(@Min(value = 1, message = "ID必须大于0") @PathVariable Long id);

    @GetMapping("/user/merchantSupplement/getIsValidShop/{id}")
    public Result<Boolean> getIsValidShop(@Min(value = 1, message = "ID必须大于0") @PathVariable Long id);

}
