package com.wddyxd.productservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.SelectGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.productservice.pojo.DTO.CouponDTO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import com.wddyxd.productservice.service.Interface.ICouponService;
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
 * @create: 2025-12-02 13:43
 **/
@RestController
@RequestMapping("/product/coupon")
@Tag(name = "优惠券控制器", description = "优惠券相关接口")
@Validated
public class CouponController {

    @Autowired
    private ICouponService couponService;

    private static final Logger log = LoggerFactory.getLogger(CouponController.class);

    @GetMapping("/list")
    //需要coupon.list权限而且(访问者的id等于参数的userId或访问者是管理员)
    @Operation(summary = "分页查询优惠券列表接口", description = "在后台和商户端的优惠券管理界面查看平台的所有优惠券或当前商户的优惠券," +
            "或在商品管理界面查看商品可用的优惠券")
    public Result<Page<Coupon>> list(@Validated(SelectGroup.class) @RequestBody SearchDTO searchDTO){

//        传入CouponListDTO,在后台和商户端的商品管理界面查看所有存在的优惠券或当前商户的优惠券,
//- 在mysql为优惠券名建立索引以支持关键字搜索
//- 返回List<CouponVO>并由PageResult包装
//- 注意无论优惠券是否被逻辑删除都应该被被查到
        log.info("用户查看优惠券列表");
        return Result.success(couponService.List(searchDTO));
    }

    @GetMapping("/detail/{id}")
    //需要coupon.list权限而且(访问者的id等于参数的userId或访问者是管理员)
    @Operation(summary = "查看某商户的某商品可用的优惠券列表接口", description = "查看商品详情接口调用该接口")
    public Result<List<Coupon>> detail(@PathVariable @Min(value = 1, message = "ID必须大于0") Long id){

//        传入商品的id,返回List<CouponVO>,查看某商户的某商品的所有未过期未被删除的优惠券,
//- 另外的,对于全场通用优惠券没有筛选条件,对于指定商户的优惠券返回该商户的优惠券,对于指定商品的优惠券返回该商品的优惠券
        return Result.success(couponService.detail(id));
    }
    @GetMapping("/visit/{id}")
    //需要coupon.list权限而且(访问者的id等于参数的userId或访问者是管理员)
    @Operation(summary = "查看某用户在某商品可用的优惠券列表接口", description = "由访问商品接口调用该接口")
    public Result<List<Coupon>> visit(@PathVariable @Min(value = 1, message = "ID必须大于0") Long id){

//        传入商品的id,返回List<CouponVO>,查看自己所有的已领取的未过期未被删除且未被使用的优惠券,
//- 另外的,对于全场通用优惠券没有筛选条件,对于指定商户的优惠券返回该商户的优惠券,对于指定商品的优惠券返回该商品的优惠券
        return Result.success(couponService.visit(id));
    }

    @PostMapping("/add")
    //需要coupon.add权限而且(访问者的id等于参数的userId或访问者是管理员)
    @Operation(summary = "添加优惠券接口", description = "添加优惠券")
    public Result<Void> add(@Validated(AddGroup.class) @RequestBody CouponDTO couponDTO){
//        传入CouponAddDTO,添加优惠券,注意相关数据是正数值,如果targetId有效须判断指向对象是否没有被删除
        couponService.add(couponDTO);
        return Result.success();
    }

    @PutMapping("/update")
    //需要coupon.update权限而且(访问者的id等于参数的userId或访问者是管理员)
    @Operation(summary = "修改优惠券接口", description = "修改优惠券")
    public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody CouponDTO couponDTO){
//        传入CouponDTO,修改优惠券,注意相关数据是正数值,如果targetId有效须判断指向对象是否没有被删除
        couponService.update(couponDTO);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    //需要coupon.delete权限而且(访问者的id等于参数的userId或访问者是管理员)
    @Operation(summary = "删除优惠券接口", description = "删除优惠券")
    public Result<Void> delete(@PathVariable @Min(value = 1, message = "ID必须大于0") Long id){
//       删除优惠券,删除时先回收用户领取的优惠券,如果优惠券不是全场通用却没有绑定合法的商户或商品则删除后打日志
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
