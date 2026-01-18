package com.wddyxd.orderservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;
import com.wddyxd.orderservice.service.Interface.ICommonOrderStatusLogService;
import com.wddyxd.orderservice.service.Interface.IPaymentOrderStatusLogService;
import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.StateMachineTrigger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class OrderStatusLogController {

    @Autowired
    private StateMachineTrigger stateMachineTrigger;

    private static final Logger log = LoggerFactory.getLogger(OrderStatusLogController.class);

    @Autowired
    private ICommonOrderStatusLogService commonOrderStatusLogService;

    @Autowired
    private IPaymentOrderStatusLogService paymentOrderStatusLogService;

    @GetMapping("/list/{id}")
    //需要orderStatusLog.list权限
    @Operation(summary = "列出订单状态接口", description = "查看订单的详细信息时调用该接口")
    public Result<List<OrderStatusLog>> list(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id){
//       传入订单id返回List<OrderStatusLog>
        log.info("列出订单状态");
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

//    @GetMapping(value = "/pay", produces = "text/html")
//    @ResponseBody
//    public String pay(@RequestParam long id) throws AlipayApiException {
//      ......
//    }

    @PostMapping("/pay/{id}")
    //需要orderStatusLog.add权限且访问者的id等于订单的userId
    @Operation(summary = "订单支付接口", description = "用户支付订单")
    public Result<String> pay(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id){
//       传入订单id,检查订单是否处于 "待支付" 状态,
//       是则生成正在支付的财务
//       调用支付接口,然后接收回调,如果有回调则根据回调结果来生成最终财务,比如支付成功/支付失败
//       如果没有成功更新最终财务就要主动查单
//       次日要进行T+1对账
//       最后生成order_status_log表和更新order_main表
//- 后续接入支付服务器,然后调用修改订单接口,解决幂等性问题,调用新增财务接口
        log.info("订单支付");
//        stateMachineTrigger.doAction(id, OrderEvent.PAY)
        return Result.success(paymentOrderStatusLogService.pay(id));
    }
    @PostMapping("/ship/{id}")
    //需要orderStatusLog.add权限且访问者的id等于订单的userId且是商家
    @Operation(summary = "订单发货接口", description = "商家为订单发货")
    public Result<Void> ship(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id){
//       传入订单id,检查订单是否处于 "已支付" 状态,直接生成order_status_log表,
//       然后调用修改订单接口,解决幂等性问题
        log.info("订单发货");
        stateMachineTrigger.doAction(id, OrderEvent.SHIP);
        return Result.success();
//        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
    @PostMapping("/receive/{id}")
    //需要orderStatusLog.add权限且访问者的id等于订单的userId
    @Operation(summary = "订单收货接口", description = "用户为订单收货")
    public Result<Void> receive(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id){
//       传入订单id,检查订单是否处于 "已发货" 状态,直接生成order_status_log表,
//- 然后调用修改订单接口,解决幂等性问题
        log.info("订单收货");
        stateMachineTrigger.doAction(id, OrderEvent.RECEIVE);
        return Result.success();
//        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
    @PostMapping("/cancel/{id}")
    //需要orderStatusLog.add权限
    @Operation(summary = "取消订单接口", description = "订单超时为支付时触发")
    public Result<Void> cancel(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id){
//       传入订单id,检查订单是否处于 "待支付" 状态,直接生成order_status_log表,
//- 然后恢复商品规格库存,然后退还优惠券给用户,然后调用修改订单接口,解决幂等性问题
        log.info("取消订单");
        stateMachineTrigger.doAction(id, OrderEvent.CANCEL);
        return Result.success();
//        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
    @PostMapping("/rollback/{id}")
    //需要orderStatusLog.add权限
    @Operation(summary = "订单退货接口", description = "用户主动退货或商家被迫退货触发")
    public Result<Void> rollback(@PathVariable @Min(value = 1L, message = "id不能小于1") Long id){
//       传入订单id,检查订单是否不是处于 "待支付" 状态,直接生成order_status_log表,
//- 然后将已支付的钱退回给用户,然后恢复商品规格库存,然后退还优惠券给用户,然后调用修改订单接口,解决幂等性问题
//- 不编写退货后退回已收货的商品的业务逻辑
        log.info("订单退货");
        paymentOrderStatusLogService.rollback(id);
        return Result.success();
//        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    //设置状态机:订单有6种状态,Integer status;//0-待付款 1-待发货 2-待收货 3-已完成 4-已取消
    //待付款可以通过调用pay方法变成待发货,
    //待发货可以通过调用ship方法变成待收货,
    //待收货可以通过调用receive方法变成已完成,
    //只有订单在待付款时可以调用cancel方法变成已取消,否则可以调用rollback方法变成已取消,



    //状态机实验:
    //步骤
    //1. 配置状态和事件常量
    //2. 创建状态机配置类,要用工厂而不是单例,然后配置默认状态和状态改变规则,还要用导入尝试状态器来补强
    //3. service层传入订单来导入初始状态,判断状态和进行事件,然后根据条件判断来调用具体业务,还可以用监听器补强
    //坑
    //1. 高并发下状态机配置类不能用单例,要用工厂类
    //2. 可以用状态机池也可以为某个线程创建一个状态机,这里采用后者方法
    //3. 用Persist调用数据库获取并载入订单状态到状态机是更佳的实践

}
