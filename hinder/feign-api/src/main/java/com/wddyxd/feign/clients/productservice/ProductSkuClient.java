package com.wddyxd.feign.clients.productservice;


import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.productservice.ProductClientFallbackFactory;
import com.wddyxd.feign.fallback.productservice.ProductSkuClientFallbackFactory;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-26 00:59
 **/

@FeignClient(value = "product-service",
        contextId = "productSkuClient",
        fallbackFactory = ProductSkuClientFallbackFactory.class)
public interface ProductSkuClient {

    @PutMapping("/update/consume")
    //需要product.update权限而且访问者的id等于参数的userId
    @Operation(summary = "修改商品规格接口", description = "在创建商品后编辑商品时可用")
    public Result<Void> updateConsume(@RequestParam Long skuId, @RequestParam Integer quantity);

}
