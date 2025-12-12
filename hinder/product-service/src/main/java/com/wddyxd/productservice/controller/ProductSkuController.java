package com.wddyxd.productservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.productservice.pojo.DTO.ProductSkuDTO;
import com.wddyxd.productservice.pojo.VO.ProductSkuVO;
import com.wddyxd.productservice.service.Interface.IProductSkuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-02 13:16
 **/
@RestController
@RequestMapping("/product/productSku")
@Tag(name = "商品规格控制器", description = "商品规格相关接口")
public class ProductSkuController {

    @Autowired
    private IProductSkuService productSkuService;

    @GetMapping("/list/{id}")
    //任何人登录后可访问
    @Operation(summary = "分页查询商品分类列表接口", description = "查看某个商品的商品规格的详细信息,通常在调用商品详情接口中远程调用该接口")
    public Result<List<ProductSkuVO>> list(@PathVariable Long id){

//        查看某个商品的商品规格的详细信息,返回List<ProductSkuVO>,不能查询到被删除的商品规格
        return Result.success(productSkuService.List(id));
    }

    @PostMapping("/add")
    //需要product.add权限而且访问者的id等于参数的userId
    @Operation(summary = "添加商品规格接口", description = "在创建商品后编辑商品时可用")
    public Result<Void> add(@RequestBody ProductSkuDTO productSkuDTO){
//        传入ProductSkuDTO,添加商品规格后,要刷新商品的库存,注意商品规格要指向合法的商品
        productSkuService.add(productSkuDTO);
        return Result.success();
    }

    @PutMapping("/update")
    //需要product.update权限而且访问者的id等于参数的userId
    @Operation(summary = "修改商品规格接口", description = "在创建商品后编辑商品时可用")
    public Result<Void> update(@RequestBody ProductSkuDTO productSkuDTO){
//        传入ProductSkuDTO,更新商品规格后,要刷新商品的库存,注意商品规格要指向合法的商品
        productSkuService.update(productSkuDTO);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    //需要product.delete权限而且(访问者的id等于参数的userId或者访问者是管理员)
    @Operation(summary = "删除商品规格接口", description = "删除商品的规格")
    public Result<Void> delete(@PathVariable Long id){
//       删除商品的规格,但不能删除该商品的最后一个商品规格,
//- 也不能删除商品的默认规格,如果规格无法指向具体商品则在删除后跳过,删除后修改商品库存
        productSkuService.delete(id);
        return Result.success();
    }

}
