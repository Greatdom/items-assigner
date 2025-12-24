package com.wddyxd.orderservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.orderservice.pojo.entity.OrderAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
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

    @PostMapping("/add")
    //需要order.add权限而且访问者的id等于参数的userId
    @Operation(summary = "新增订单地址接口", description = "用户在下单时调用该接口")
    public Result<Void> add(){
//        根据token得到用户信息,从而得到默认地址并生成订单地址
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @GetMapping("/detail/{id}")
    //需要order.list权限而且(访问者的id等于订单的userId或访问者是管理员)
    @Operation(summary = "查询特定订单地址接口", description = "查询订单的唯一的订单地址")
    public Result<OrderAddress> detail(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id){

        //        传入订单id,从而查询订单地址OrderAddress
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PutMapping("/update/{id}")
    //需要order.update权限而且访问者的id等于参数的userId
    @Operation(summary = "修改订单地址接口", description = "用户可用在订单收货前修改订单地址")
    public Result<Void> update(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id){
//       传入用户地址id,从而修改订单地址
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
