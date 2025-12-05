package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.userservice.pojo.entity.RolePermission;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 20:27
 **/

public interface IRolePermissionService extends IService<RolePermission> {

    public void assign(Long roleId, Long[] permissionIds);

}
