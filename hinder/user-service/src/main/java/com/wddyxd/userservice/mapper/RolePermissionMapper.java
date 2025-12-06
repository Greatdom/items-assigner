package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: items-assigner
 * @description: rolepermission 映射接口
 * @author: wddyxd
 * @create: 2025-10-20 20:59
 **/
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
