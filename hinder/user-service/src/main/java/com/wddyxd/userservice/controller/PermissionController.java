package com.wddyxd.userservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.entity.Permission;
import com.wddyxd.userservice.service.Interface.IPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: items-assigner
 * @description: 权限接口控制器
 * @author: wddyxd
 * @create: 2025-10-20 21:06
 **/

@RestController
@RequestMapping("/user/permission")
@Tag(name = "权限控制器", description = "权限相关接口")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/list")
    //需要permission.list权限
    @Operation(summary = "分页获取权限列表接口", description = "在管理员的权限管理主界面查看所有存在的权限")
    public Result<Page<Permission>> list(@RequestBody SearchDTO searchDTO){

        return Result.success(permissionService.List(searchDTO));
    }

    public Result<Void> assign(
            @RequestParam
            @Min(value = 1, message = "角色ID必须大于0") // 校验roleId > 0
            Long roleId,

            @RequestParam
            @NotNull(message = "权限ID数组不能为空") // 校验数组非空
            @Valid // 触发数组内元素的校验（需配合自定义注解或Spring 6+的数组元素校验）
            @Min(value = 1, message = "权限ID必须大于0") // 校验数组中每个元素 > 0
            Long[] permissionIds) {

        permissionService.assign(roleId, permissionIds);
        return Result.success();
    }

    @PostMapping("/add")
    //需要permission.add权限
    @Operation(summary = "添加权限接口", description = "管理员可以在权限管理界面添加权限")
    public Result<Void> add(@RequestParam String name,@RequestParam String permissionValue){
//        添加权限
        permissionService.add(name, permissionValue);
        return Result.success();
    }

    @PutMapping("/update")
    //需要permission.update权限
    @Operation(summary = "更改权限内容接口", description = "管理员可以在权限管理界面更新权限信息")
    public Result<Void> update(@RequestParam Long id, @RequestParam String name,@RequestParam String permissionValue){
//        更新权限信息,权限被逻辑删除则拒绝更新
        permissionService.update(id,name, permissionValue);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    //需要permission.delete权限
    @Operation(summary = "删除权限接口", description = "只有超级管理员有权限删除权限,删除权限按钮在权限管理界面")
    public Result<Void> delete(@PathVariable Long id){
//        删除权限包括根据id将permissions表的主键等于id,
//- role_permissions表permissions_id等于id的行逻辑删除
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }


}
