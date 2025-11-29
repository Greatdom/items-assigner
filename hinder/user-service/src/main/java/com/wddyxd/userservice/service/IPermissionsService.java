package com.wddyxd.userservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.userservice.pojo.entity.Permission;

import java.util.List;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 09:37
 **/

public interface IPermissionsService extends IService<Permission> {


    void assignPermissions(Long roleId, Long[] permissionId);

    List<String> selectPermissionValueByUserId(Long id);


}
