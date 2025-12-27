package com.wddyxd.orderservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.orderservice.pojo.entity.OrderAddress;
import com.wddyxd.orderservice.service.impl.IOrderMainServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 12:50
 **/

@RestController
@RequestMapping("/order/orderStatus")
@Tag(name = "订单地址控制器", description = "订单地址相关接口")
@Validated
public class OrderAddressController {

    private static final Logger log = LoggerFactory.getLogger(OrderAddressController.class);

    @PutMapping("/update/{id}")
    //需要order.update权限而且访问者的id等于参数的userId
    @Operation(summary = "修改订单地址接口", description = "用户可用在订单收货前修改订单地址")
    public Result<Void> update(@PathVariable @Min(value = 1L, message = "id不能小于1") Long userAddressId,
                               @PathVariable @Min(value = 1L, message = "id不能小于1") Long orderAddressId
                               ){
//       传入特定用户地址id和订单地址id,从而修改订单地址
        log.info("修改订单地址");
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
