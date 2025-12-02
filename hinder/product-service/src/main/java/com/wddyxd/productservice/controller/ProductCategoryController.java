package com.wddyxd.productservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-02 13:06
 **/
@RestController
@RequestMapping("/product/productCategory")
@Tag(name = "商品分类控制器", description = "商品分类相关接口")
public class ProductCategoryController {

    @GetMapping("/list")
    //需要productCategory.list权限
    @Operation(summary = "分页查询商品分类列表接口", description = "在管理员的商品分类管理主界面查看所有存在的商品分类")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "") String search){

//        在管理员的商品分类主界面查看所有存在的权限,支持根据关键字搜索,
//- 在mysql为分类名建立索引以支持关键字搜索
//- 返回List<ProductCategory>并由PageResult包装
//- 注意无论用户是否被逻辑删除都应该被被查到
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PostMapping("/add")
    //需要productCategory.add权限
    @Operation(summary = "增加商品分类接口", description = "管理员可以在商品分类管理界面添加商品分类")
    public Result<?> add(@RequestParam String name){
//        添加商品分类
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PutMapping("/update")
    //需要productCategory.update权限
    @Operation(summary = "修改商品分类接口", description = "管理员可以在商品分类管理界面更新商品分类信息")
    public Result<?> update(@RequestParam String name){
//        更新商品分类信息,商品分类被逻辑删除则拒绝更新
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    //需要productCategory.delete权限
    @Operation(summary = "删除商品分类接口", description = "只有管理员有权限删除商品分类,删除商品分类按钮在商品分类管理界面")
    public Result<?> delete(@PathVariable Long id){
//       删除商品分类包括根据id将product_category表的主键等于id的行逻辑删除,
//- 不能删除"默认商品分类"即第一个分类,然后遍历商品,如果商品有该商品分类则将该商品分类变成"默认商品分类"
//- 只有超级管理员才可以删除商品分类
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
