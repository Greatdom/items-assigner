package com.wddyxd.userservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantCustomDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantLicenseDTO;
import com.wddyxd.userservice.service.Interface.IMerchantSupplementService;
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
 * @create: 2025-12-01 19:00
 **/
@RestController
@RequestMapping("/user/merchantSupplement")
@Tag(name = "商户补丁控制器", description = "商户补丁相关接口")
@Validated
public class MerchantSupplementController {

    @Autowired
    private IMerchantSupplementService merchantSupplementService;

    @GetMapping("/getShopName/{id}")
    @Operation(summary = "查看商户名接口", description = "远程调用接口")
    public Result<String> getShopName(@Min(value = 1, message = "ID必须大于0") @PathVariable Long id){
        return Result.success(merchantSupplementService.getShopName(id));
    }

    @DeleteMapping("/delete/{id}")
    //需要user.delete权限
    @Operation(summary = "删除商户接口", description = "只有管理员有权限删除商户,普通商户要注销需要将注销请求发送给管理员")
    public Result<Void> delete(@Validated(UpdateGroup.class) @PathVariable Long id){
//       在删除账户之前在redis查看上次删除该用户的时间戳如果存在则直接返回,
//- 否则要强制取消和退货为完成的订单(调用取消和退货订单接口)
//- 然后先强制下架商品再删除商品(调用删除商品接口,其中回收所有的用户领取的自己发放的优惠券,然后将该商家的优惠劵折现删除),
//- 然后将唯一约束字段(都是varchar字段)修改成 "原先字段.DELETED.删除时间戳"的格式,然后将merchant_supplement表逻辑删除
//- 然后将用户和商户相关角色的关联逻辑删除
//- 然后在redis添加本次删除的时间戳
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PutMapping("/update/custom")
    //访问者的id等于参数的id
    @Operation(summary = "更新基础商户信息接口", description = "更新基础商户信息")
    public Result<Void> updateCustom(@Validated(UpdateGroup.class) @RequestBody UpdateMerchantCustomDTO updateMerchantCustomDTO){
//        传入UpdateMerchantCustomDTO,更新基础商户信息,查询不到用户或用户被逻辑删除则不应该执行更新
        merchantSupplementService.updateCustom(updateMerchantCustomDTO);
        return Result.success();
    }

    @PutMapping("/update/license")
    //更新者的id等于参数id
    @Operation(summary = "许可证认证接口", description = "许可证认证")
    public Result<Void> updateLicense(@Validated(UpdateGroup.class) @RequestBody UpdateMerchantLicenseDTO updateMerchantLicenseDTO){
//        传入UpdateMerchantLicenseDTO,提供许可证后异步通知管理员审核,应该审核后管理员手动为其升级角色,
//- 但这里实现为直接为该用户升级角色
//- 查询不到用户或用户被逻辑删除则不应该执行更新
        merchantSupplementService.updateLicense(updateMerchantLicenseDTO);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    //更新者的id等于参数id
    @Operation(summary = "开张/关店接口", description = "开张/关店")
    public Result<Void> status(@Min(value = 1, message = "ID必须大于0") @PathVariable Long id){
//        传入用户id,开张/关店,此时用户无法在这家店发起订单并强制取消或退货还没有完成的订单
//- 查询不到用户或用户被逻辑删除则跳过
        merchantSupplementService.status(id);
        return Result.success();
    }

}
