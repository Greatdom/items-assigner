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


    @PostMapping("/doAssign")
    public Result<?> doAssign(@RequestParam Long roleId,@RequestParam Long[] permissionIds) {
            permissionsService.assignPermissions(roleId,permissionIds);
            return Result.success();
    }

    //CRUD
    @GetMapping("/all")
    public Result<List<Permissions>> SelectAll(){
            return Result.success(permissionsService.list());
    }
    @GetMapping("/one/{id}")
    public Result<Permissions> SelectById(@PathVariable Long id){
        return Result.success(permissionsService.getById(id));
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
