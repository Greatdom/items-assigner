package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.entity.Permissions;

import java.util.List;

/**
 * @program: items-assigner
 * @description: permission 数据库接口
 * @author: wddyxd
 * @create: 2025-10-20 20:58
 **/

public interface PermissionsMapper extends BaseMapper<Permissions> {
    List<String> selectPermissionValueByUserId(Long id);
}
