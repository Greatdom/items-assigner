package com.wddyxd.feign.clients.productservice;


import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.productservice.ProductClientFallbackFactory;
import com.wddyxd.feign.pojo.productservice.ProductDetailVO;
import com.wddyxd.feign.pojo.productservice.ProductProfileVO;
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
    public Result<ProductDetailVO> visit(@PathVariable @Min(value = 1, message = "ID必须大于0") Long id);

    @GetMapping("/product/product/get/{id}")
    public Result<ProductProfileVO> get(@PathVariable @Min(value = 1, message = "ID必须大于0") Long id);

}
