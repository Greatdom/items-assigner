package com.wddyxd.userservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.VO.RoleVO;
import com.wddyxd.userservice.pojo.entity.Role;
import com.wddyxd.userservice.service.Interface.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: items-assigner
 * @description: 角色接口控制器
 * @author: wddyxd
 * @create: 2025-10-20 21:05
 **/

@RestController
@RequestMapping("/user/role")
@Tag(name = "角色控制器", description = "角色相关接口")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    //需要role.list权限
    @Operation(summary = "分页获取角色列表接口", description = "在管理员的角色管理主界面查看所有存在的角色")
    public Result<Page<Role>> list(@RequestBody SearchDTO searchDTO){
        return Result.success(roleService.List(searchDTO));
    }

    @GetMapping("/detail/{id}")
    //需要role.list权限
    @Operation(summary = "查看角色详细信息接口", description = "在管理员的角色管理主界面查看角色详情")
    public Result<RoleVO> detail(@PathVariable Long id){
        return Result.success(roleService.detail(id));
    }

    @PostMapping("/assign")
    //需要user.update权限
    @Operation(summary = "手动为特定用户分配角色接口", description = "管理员可以在用户管理界面查看用户详细信息的时候为某用户分配角色")
    public Result<Void> assign(@RequestParam Long userId, @RequestParam Long[] roleIds){
//        一个用户可以分配最多三个角色,分别是最多一个购物者角色,一个商户角色和一个管理员角色,
//- 如果发现roleId不满足分配角色合法性则拒绝分配,
//- 否则根据参数查询用户和用户的角色,然后删除用户拥有的角色,然后重新增加角色
//- 如果原先没有商户角色但添加了商户则创建商户表,并插入默认商户信息(但如果给了最高级商户角色但没有许可证呢Void)
//- 只有超级管理员才可以给用户分配管理员角色,管理员只能给用户分配除管理员外的角色
//- 如果查询不到用户或用户被逻辑删除则拒绝分配
        roleService.assign(userId, roleIds);
        return Result.success();
    }

    @PostMapping("/add")
    //需要role.add权限
    @Operation(summary = "添加角色接口", description = "管理员可以在角色管理界面添加角色")
    public Result<Void> add(@RequestParam String name,@RequestParam Integer group){
//        添加角色
        roleService.add(name,group);
        return Result.success();
    }

    @PutMapping("/update")
    //需要role.update权限
    @Operation(summary = "更新角色内容接口", description = "管理员可以在角色管理界面更新角色信息")
    public Result<Void> update(@RequestBody Role role){
//        更新角色信息,角色被逻辑删除则拒绝更新
        roleService.update(role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    //需要role.delete权限
    @Operation(summary = "删除店铺分类接口", description = "只有超级管理员有权限删除角色,删除角色按钮在角色管理界面")
    public Result<Void> delete(@PathVariable Long id){
//        删除角色包括根据id将role表的主键等于id,
//- role_permissions表role_id等于id和user_role表role_id等于id的行逻辑删除,
//- 不能删除每个角色组的"默认角色",然后遍历用户,如果用户有该角色则将该角色变成所在组的"默认角色"
//- 只有超级管理员才可以删除角色
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
