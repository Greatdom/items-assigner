package com.wddyxd.productservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.productservice.pojo.VO.UserCouponVO;
import com.wddyxd.productservice.service.Interface.IUserCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
 * @create: 2025-12-02 14:51
 **/

@RestController
@RequestMapping("/product/userCoupon")
@Tag(name = "用户的优惠券控制器", description = "用户的优惠券相关接口")
@Validated
public class UserCouponController {

    @Autowired
    private IUserCouponService userCouponService;

    private static final Logger log = LoggerFactory.getLogger(UserCouponController.class);

    @GetMapping("/list")
    //需要userCoupon.list权限且访问者的id等于参数的userId
    @Operation(summary = "查询某用户领取的可用优惠券列表接口", description = "在用户端的个人中心的优惠券界面查看自己领取的优惠券")
    public Result<List<UserCouponVO>> list(){

//        返回List<UserCouponVO>,查看自己所有的已领取未被删除的优惠券,
//- 要保证用户领取的优惠券指向的优惠券合法
        return Result.success(userCouponService.List());
    }

    @PostMapping("/add/{id}")
    //需要userCoupon.add权限且访问者的id等于参数的userId
    @Operation(summary = "领取优惠券接口", description = "用户进行领取优惠券操作")
    public Result<Void> add(@PathVariable @Min(value = 1L, message = "优惠券id不能小于1") Long id){
//        领取优惠券,只有在优惠券没被删除,在生效的时候才可执行操作,
//- 要注意一人一券,就要试图解决高并发问题,然后要将优惠券的库存减少
        userCouponService.add(id);
        return Result.success();
    }

    @PutMapping("/consume/{id}/{orderId}")
    //需要userCoupon.update权限且访问者的id等于参数的userId
    @Operation(summary = "消费优惠券接口", description = "管理员可以在商品分类管理界面更新商品分类信息")
    public Result<Void> consume(@PathVariable @NotNull(message = "优惠券id不能为空") Long[] couponIds,
                                @PathVariable @Min(value = 1L, message = "优惠券id不能小于1") Long orderId){
//        用户在下单时进行优惠券的消费,传入orderId后生成useTime,status代表该优惠券被消费
        userCouponService.consume(couponIds,orderId);
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @DeleteMapping("/destroy/{id}")
    //需要userCoupon.delete权限
    @Operation(summary = "销毁用户的优惠券接口", description = "由于一些原因,触发优惠券的删除,这时会回收用户领取的优惠券,在优惠券被删除时调用接口")
    public Result<Void> destroy(@PathVariable @Min(value = 1L, message = "优惠券id不能小于1") Long id){
//       传入优惠券的id,批量逻辑删除该couponId指向的所有用户领取的优惠券,然后减去优惠券的库存
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
