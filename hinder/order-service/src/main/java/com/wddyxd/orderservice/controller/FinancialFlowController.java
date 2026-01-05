package com.wddyxd.orderservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.SelectGroup;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.orderservice.pojo.DTO.FinancialFlowDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 12:51
 **/

@RestController
@RequestMapping("/order/financialFlow")
@Tag(name = "财务控制器", description = "财务相关接口")
@Validated
public class FinancialFlowController {


    @GetMapping("/list")
    //需要financialFlow.list权限
    @Operation(summary = "分页查询财务接口", description = "后台端在订单管理页面查看网站的所有财务列表")
    public Result<Void> list(@Validated(SelectGroup.class) @RequestBody SearchDTO searchDTO){

//        返回List<FinancialFlow>并由PageResult包装
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
