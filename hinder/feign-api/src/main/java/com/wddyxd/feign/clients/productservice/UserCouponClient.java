package com.wddyxd.feign.clients.productservice;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.productservice.ProductSkuClientFallbackFactory;
import com.wddyxd.feign.fallback.productservice.UserCouponClientFallbackFactory;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-26 01:22
 **/

@FeignClient(value = "product-service",
        contextId = "userCouponClient",
        fallbackFactory = UserCouponClientFallbackFactory.class)
public interface UserCouponClient {

    @PutMapping("/product/userCoupon/consume")
    Result<List<Long>> consume(
            @RequestParam("couponIds") @NotNull(message = "优惠券id不能为空") Long[] couponIds,
            @RequestParam("orderId") @Min(value = 1L, message = "优惠券id不能小于1") Long orderId
    );
}
