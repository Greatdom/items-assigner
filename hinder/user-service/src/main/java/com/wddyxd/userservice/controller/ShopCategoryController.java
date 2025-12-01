package com.wddyxd.userservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.DTO.UserAddressDTO;
import com.wddyxd.userservice.pojo.entity.ShopCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-01 19:13
 **/

@RestController
@RequestMapping("/user/shopCategory")
@Tag(name = "店铺分类控制器", description = "店铺分类相关接口")
public class ShopCategoryController {
    @GetMapping("/list")
    //需要shopCategory.list权限
    @Operation(summary = "分页查询店铺分类接口", description = "在管理员的角色管理主界面查看所有存在的店铺分类")
    public Result<ShopCategory> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "") String search){

//        在管理员的角色管理主界面查看所有存在的店铺分类,支持根据关键字搜索,
//- 在mysql为店铺分类名建立索引以支持关键字搜索
//- 返回List<ShopCategory>并由PageResult包装
//- 注意无论店铺分类是否被逻辑删除都应该被被查到
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PostMapping("/add")
    //需要shopCategory.add权限
    @Operation(summary = "增加店铺分类接口", description = "管理员可以在店铺分类界面增加店铺分类")
    public Result<?> add(@RequestParam String name){
//        增加店铺分类
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PutMapping("/update")
    //需要shopCategory.update权限
    @Operation(summary = "更新店铺分类内容接口", description = "管理员可以在店铺分类管理界面更新店铺分类信息")
    public Result<?> update(@RequestParam String name){
//        更新店铺分类信息,店铺分类被逻辑删除则拒绝更新
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    //需要shopCategory.delete权限
    @Operation(summary = "删除店铺分类接口", description = "只有管理员有权限删除店铺分类,删除店铺分类按钮在店铺分类管理界面")
    public Result<?> delete(@PathVariable Long id){
//       删除店铺分类,然后遍历merchant_supplement表,将涉及被删除分类的记录变成"默认分类"
//- 只有超级管理员才可以删除店铺分类
// 数据库第一个分类是默认分类,不能删除默认分类
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
