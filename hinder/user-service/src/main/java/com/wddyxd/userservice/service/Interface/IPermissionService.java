package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.pojo.entity.Permission;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 09:37
 **/

public interface IPermissionService extends IService<Permission> {


    public Page<Permission> List(SearchDTO searchDTO);

    public void assign(Long roleId,Long[] permissionIds);

    public void add(String name,String permissionValue);

    public void update(String name,String permissionValue);

    public void delete(Long id);
}
