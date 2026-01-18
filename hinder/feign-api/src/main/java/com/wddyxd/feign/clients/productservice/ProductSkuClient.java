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

    @PutMapping("/product/productSku/update/consume")
    public Result<Void> updateConsume(@RequestParam Long skuId, @RequestParam Integer quantity);

}
