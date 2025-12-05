package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.entity.Permission;
import org.apache.ibatis.annotations.Param;

/**
 * @program: items-assigner
 * @description: permission 数据库接口
 * @author: wddyxd
 * @create: 2025-10-20 20:58
 **/

public interface PermissionMapper extends BaseMapper<Permission> {
    boolean checkAllPermissionIdsValid(@Param("ids") Long[] ids);
}
