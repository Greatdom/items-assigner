package com.wddyxd.feign.clients.productservice;


import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.productservice.ProductClientFallbackFactory;
import com.wddyxd.feign.pojo.productservice.ProductDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-25 21:56
 **/

@FeignClient(value = "product-service",
        contextId = "productClient",
        fallbackFactory = ProductClientFallbackFactory.class)
public interface ProductClient {

    @GetMapping("/product/product/visit/{id}")
    //任何用户登录后可访问
    @Operation(summary = "在用户端访问商品接口", description = "在用户端点击被推送的商品可以访问该商品")
    public Result<ProductDetailVO> visit(@PathVariable @Min(value = 1, message = "ID必须大于0") Long id);

}
