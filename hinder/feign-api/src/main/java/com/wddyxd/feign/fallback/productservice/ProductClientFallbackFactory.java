package com.wddyxd.feign.fallback.productservice;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.productservice.ProductClient;
import com.wddyxd.feign.clients.userservice.AuthClient;
import com.wddyxd.feign.fallback.userservice.AuthClientFallbackFactory;
import com.wddyxd.feign.pojo.productservice.ProductDetailVO;
import com.wddyxd.feign.pojo.userservice.authcontroller.CurrentUserDTO;
import com.wddyxd.feign.pojo.userservice.authcontroller.PasswordSecurityGetterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-25 21:57
 **/
@Component
public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {

    static final Logger log = LoggerFactory.getLogger(ProductClientFallbackFactory.class);

    @Override
    public ProductClient create(Throwable cause) {
        return new ProductClient() {
            @Override
            public Result<ProductDetailVO> visit(Long id) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
        };
    }

}
