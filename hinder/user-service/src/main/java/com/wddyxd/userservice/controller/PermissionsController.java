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


    //TODO 给角色分配权限
    @PostMapping("/doAssign")
    public Result<?> doAssign(@RequestParam Long roleId,@RequestParam Long[] permissionIds) {
            permissionsService.assignPermissions(roleId,permissionIds);
            return Result.success();
    }

    //TODO 根据角色获取菜单
//    @GetMapping("/all")
//    public Result<List<Permissions>> SelectAll(){
//        return Result.success(permissionsService.queryAllMenus());
//    }





    //CRUD
    @GetMapping("/all")
    public Result<List<Permissions>> SelectAll(){
            return Result.success(permissionsService.list());
    }
    @GetMapping("/one")
    public Result<?> SelectOne(){
        //TODO 查询个人权限
        return null;
    }
    @PostMapping("/add")
    public Result<Permissions> add(@RequestBody Permissions permissions){
        try {
            permissionsService.save(permissions);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
        }
    }



}
