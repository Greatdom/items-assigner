package com.wddyxd.orderservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.orderservice.pojo.DTO.OrderUpdateDTO;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 12:36
 **/
@RestController
@RequestMapping("/order/orderStatusLog")
@Tag(name = "订单状态控制器", description = "订单状态相关接口")
public class OrderStatusLogController {

    @GetMapping("/list/{id}")
    //需要orderStatusLog.list权限
    @Operation(summary = "列出订单状态接口", description = "查看订单的详细信息时调用该接口")
    public Result<List<OrderStatusLog>> list(@PathVariable Long id){
//       传入订单id返回List<OrderStatusLog>
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PostMapping("/pay/{id}")
    //需要orderStatusLog.add权限且访问者的id等于订单的userId
    @Operation(summary = "订单支付接口", description = "用户支付订单")
    public Result<?> pay(@PathVariable Long id){
//       传入订单id,检查订单是否处于 "待支付" 状态,直接生成order_status_log表,
//- 后续接入支付服务器,然后调用修改订单接口,解决幂等性问题,调用新增财务接口
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
    @PostMapping("/ship/{id}")
    //需要orderStatusLog.add权限且访问者的id等于订单的userId且是商家
    @Operation(summary = "订单发货接口", description = "商家为订单发货")
    public Result<?> ship(@PathVariable Long id){
//       传入订单id,检查订单是否处于 "已支付" 状态,直接生成order_status_log表,
//       然后调用修改订单接口,解决幂等性问题
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
    @PostMapping("/receive/{id}")
    //需要orderStatusLog.add权限且访问者的id等于订单的userId
    @Operation(summary = "订单收货接口", description = "用户为订单收货")
    public Result<?> receive(@PathVariable Long id){
//       传入订单id,检查订单是否处于 "已发货" 状态,直接生成order_status_log表,
//- 然后调用修改订单接口,解决幂等性问题
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
    @PostMapping("/cancel/{id}")
    //需要orderStatusLog.add权限
    @Operation(summary = "取消订单接口", description = "订单超时为支付时触发")
    public Result<?> cancel(@PathVariable Long id){
//       传入订单id,检查订单是否处于 "待支付" 状态,直接生成order_status_log表,
//- 然后恢复商品规格库存,然后退还优惠券给用户,然后调用修改订单接口,解决幂等性问题
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
    @PostMapping("/rollback/{id}")
    //需要orderStatusLog.add权限
    @Operation(summary = "订单退货接口", description = "用户主动退货或商家被迫退货触发")
    public Result<?> rollback(@PathVariable Long id){
//       传入订单id,检查订单是否不是处于 "待支付" 状态,直接生成order_status_log表,
//- 然后将已支付的钱退回给用户,然后恢复商品规格库存,然后退还优惠券给用户,然后调用修改订单接口,解决幂等性问题
//- 不编写退货后退回已收货的商品的业务逻辑
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
