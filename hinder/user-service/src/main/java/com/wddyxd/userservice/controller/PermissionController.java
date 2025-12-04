package com.wddyxd.userservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.entity.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @GetMapping("/list")
    //需要permission.list权限
    @Operation(summary = "分页获取权限列表接口", description = "在管理员的权限管理主界面查看所有存在的权限")
    public Result<Page<Permission>> list(@RequestBody SearchDTO searchDTO){

//       在管理员的权限管理主界面查看所有存在的权限,支持根据关键字搜索,
//- 在mysql为权限值建立索引以支持关键字搜索
//- 返回List<Permissions>并由PageResult包装
//- 注意无论用户是否被逻辑删除都应该被被查到
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PostMapping("/assign")
    //需要role.update权限
    @Operation(summary = "手动为角色分配权限接口", description = "管理员可以在角色管理界面查看角色详细信息的时候为某角色分配权限")
    public Result<Void> assign(@RequestParam Long roleId, @RequestParam Long[] permissionIds){
//        permissionIds都指向存在的正常运作的权限才可继续执行接口,
//- 根据参数查询角色和角色的权限,然后删除角色拥有的权限,然后重新增加权限
//- 只有超级管理员才可以给角色分配权限
//- 如果角色被删除则拒绝执行接口
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PostMapping("/add")
    //需要permission.add权限
    @Operation(summary = "添加权限接口", description = "管理员可以在权限管理界面添加权限")
    public Result<Void> add(@RequestParam String name,@RequestParam String permissionValue){
//        添加权限
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PutMapping("/update")
    //需要permission.update权限
    @Operation(summary = "更改权限内容接口", description = "管理员可以在权限管理界面更新权限信息")
    public Result<Void> update(@RequestParam String name,@RequestParam String permissionValue){
//        更新权限信息,权限被逻辑删除则拒绝更新
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
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
