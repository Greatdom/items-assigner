package com.wddyxd.userservice.controller;


import com.alibaba.fastjson.JSONObject;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.Permissions;
import com.wddyxd.userservice.service.IPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: items-assigner
 * @description: 权限接口控制器
 * @author: wddyxd
 * @create: 2025-10-20 21:06
 **/

@RestController
@RequestMapping("/user/permissions")
public class PermissionsController {

    @Autowired
    private IPermissionsService permissionsService;

    @GetMapping("/menu")
    public Result<List<JSONObject>> SelectMenu(){
        //TODO获取权限菜单,要做成获取角色用户名来得到菜单
            List<JSONObject> list = permissionsService.SelectMenu();
            return Result.success(list);
    }

    //TODO 给角色分配权限
    @PostMapping("/assign")
    public Result<?> doAssign(Long roleId,Long[] permissionIds) {
            permissionsService.assignPermissions(roleId,permissionIds);
            return Result.success();
    }

//    //TODO 根据角色获取菜单
//    @GetMapping("toAssign/{roleId}")
//    public Result<List<JSONObject>> toAssign(@PathVariable String roleId) {
//        List<Permissions> list = permissionService.selectAllMenu(roleId);
//        return R.ok().data("children", list);
//    }





    //CRUD
    @GetMapping("/all")
    public Result<List<Permissions>> SelectAll(){
            return Result.success(permissionsService.queryAllMenus());
    }
    @GetMapping("/one")
    public Result<?> SelectOne(){
        //TODO 查询个人权限
        return null;
    }
    @PostMapping("/add")
    public Result<Permissions> add(@RequestBody Permissions permissions){
        permissionsService.save(permissions);
        return Result.success();
    }



}
