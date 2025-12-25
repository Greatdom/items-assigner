package com.wddyxd.orderservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.SelectGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.userservice.MerchantSupplementClient;
import com.wddyxd.orderservice.pojo.DTO.OrderDTO;
import com.wddyxd.orderservice.pojo.VO.OrderDetailVO;
import com.wddyxd.orderservice.pojo.VO.OrderProfileVO;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 12:10
 **/

@RestController
@RequestMapping("/order/orderMain")
@Tag(name = "订单控制器", description = "订单相关接口")
@Validated
public class OrderMainController {



    @PostMapping("/add")
    //需要order.add权限而且访问者的id等于参数的userId
    @Operation(summary = "新增订单接口", description = "用户进行下单操作")
    public Result<Void> add(@Validated(AddGroup.class) @RequestBody OrderDTO orderDTO){
        //        传入OrderAddDTO,将token信息和userId比对并检查用户是否可以正常使用,
        // - 然后查看商家是否在开张,
//- 然后查看指向的商品和规格是否存在,没被删除,在上架和在正常使用,
//- 然后查看用户领取的优惠券指向的优惠券是否存在,未删除且在生效而且满足使用条件,
//- 然后查询商品和商品规格,在生成的订单添加商品快照,然后减少商品和规格的库存,
//- 然后在用户领取的优惠券标记优惠券已经使用,然后计算订单总价格和实际价格,
//- 然后生成一份"待付款"的订单状态记录order_status_log,然后生成一份收货地址快照order_address.
//- 要注意超卖问题,分布事务问题,并发性能问题,幂等性问题等.
//- 添加订单备注,比如用了什么优惠券,
//- 然后将该订单存入数据库.
//- 将该订单传入定时任务,如果15分钟没有付款就执行取消订单操作

        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }


    @GetMapping("/list/user")
    //需要order.list权限而且访问者的id等于参数的userId
    @Operation(summary = "用户端分页查询订单列表接口", description = "用户端在个人中心查看订单列表")
    public Result<OrderProfileVO> listUser(@Validated(SelectGroup.class) @RequestBody SearchDTO searchDTO){

//        在用户端根据订单的状态(全部,未付款,待发货,待收货,已完成,已取消)来查询没有被删除的订单的列表,
//- 当再次访问这个接口时前一次的返回结果会保留,
//- 携带的用户信息是商家信息
//- 返回List<OrderProfileVO>并由PageResult包装
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @GetMapping("/list/merchant")
    //需要order.list权限而且访问者的id等于参数的userId而且是商户
    @Operation(summary = "商户端分页查询订单列表接口", description = "商户端在订单管理页面查看商家的所有商品的订单列表")
    public Result<OrderProfileVO> listMerchant(@Validated(SelectGroup.class) @RequestBody SearchDTO searchDTO){

//        在商户端根据订单的状态,
//- 来查询没有被删除的订单的列表,
//- 支持根据商户名称,购买者名称,商品名称和商品规格进行关键字检索
//- - 携带的用户信息是用户信息
//- 返回List<OrderProfileVO>并由PageResult包装
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @GetMapping("/list/admin")
    //需要order.list权限而且是管理员
    @Operation(summary = "后台端分页查询订单列表接口", description = "后台端在订单管理页面查看网站的所有订单列表")
    public Result<OrderProfileVO> listAdmin(@Validated(SelectGroup.class) @RequestBody SearchDTO searchDTO){

//        在后台端根据订单的状态,
//- 来查询没有被删除的订单的列表,
//- 支持根据商户名称,购买者名称,商品名称和商品规格进行关键字检索
//- - 携带的用户信息是用户信息
//- 返回List<OrderProfileVO>并由PageResult包装
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @GetMapping("/detail/{id}")
    //需要order.list权限而且(访问者的id等于订单的userId或访问者是管理员)
    @Operation(summary = "查看订单详细信息接口", description = "查看订单的详细信息,商户端和管理端点击订单时访问该接口")
    public Result<OrderDetailVO> detail(@PathVariable @Min( value = 1 , message = "ID必须大于0" ) Long id){

    //        返回OrderDetailVO,包括访问被下架或删除的订单
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PutMapping("/update")
    //需要orderStatusLog.add权限
    @Operation(summary = "修改订单信息接口", description = "在订单状态改变后被订单日志接口调用")
    public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody OrderDTO orderDTO){
//       传入OrderUpdateDTO,修改订单信息
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
