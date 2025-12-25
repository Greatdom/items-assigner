package com.wddyxd.feign.fallback.productservice;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.productservice.ProductClient;
import com.wddyxd.feign.clients.productservice.ProductSkuClient;
import com.wddyxd.feign.pojo.productservice.ProductDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-26 01:00
 **/
@Component
public class ProductSkuClientFallbackFactory implements FallbackFactory<ProductSkuClient> {

    static final Logger log = LoggerFactory.getLogger(ProductSkuClientFallbackFactory.class);

    @Override
    public ProductSkuClient create(Throwable cause) {
        return new ProductSkuClient() {
            @Override
            public Result<Void> updateConsume(Long skuId, Integer quantity) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
        };
    }

}
