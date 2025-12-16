package com.wddyxd.productservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.productservice.pojo.entity.ProductCategory;
import com.wddyxd.productservice.service.Interface.IProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class ProductCategoryController {

    @Autowired
    private IProductCategoryService productCategoryService;

    @GetMapping("/list")
    //需要productCategory.list权限
    @Operation(summary = "分页查询商品分类列表接口", description = "在管理员的商品分类管理主界面查看所有存在的商品分类")
    public Result<Page<ProductCategory>> list(@RequestBody SearchDTO searchDTO){

//        在管理员的商品分类主界面查看所有存在的权限,支持根据关键字搜索,
//- 在mysql为分类名建立索引以支持关键字搜索
//- 返回List<ProductCategory>并由PageResult包装
//- 注意无论用户是否被逻辑删除都应该被被查到
        return Result.success(productCategoryService.List(searchDTO));
    }

    @PostMapping("/add")
    //需要productCategory.add权限
    @Operation(summary = "增加商品分类接口", description = "管理员可以在商品分类管理界面添加商品分类")
    public Result<Void> add(@RequestParam @NotBlank(message = "分类名不能为空") String name){
        System.out.println("www");
//        添加商品分类
        productCategoryService.add(name);
        return Result.success();
    }

    @PutMapping("/update")
    //需要productCategory.update权限
    @Operation(summary = "修改商品分类接口", description = "管理员可以在商品分类管理界面更新商品分类信息")
    public Result<Void> update(@RequestParam @Min(value = 1, message = "ID必须大于0") Long id,
                               @RequestParam @NotBlank(message = "分类名不能为空") String name){
//        更新商品分类信息,商品分类被逻辑删除则拒绝更新
        productCategoryService.update(id, name);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    //需要productCategory.delete权限
    @Operation(summary = "删除商品分类接口", description = "只有管理员有权限删除商品分类,删除商品分类按钮在商品分类管理界面")
    public Result<Void> delete(@PathVariable @RequestParam @Min(value = 1, message = "ID必须大于0") Long id){
//       删除商品分类包括根据id将product_category表的主键等于id的行逻辑删除,
//- 不能删除"默认商品分类"即第一个分类,然后遍历商品,如果商品有该商品分类则将该商品分类变成"默认商品分类"
//- 只有超级管理员才可以删除商品分类
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
