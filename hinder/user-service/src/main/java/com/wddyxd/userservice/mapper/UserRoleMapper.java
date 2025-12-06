package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: items-assigner
 * @description: user-role 数据库接口
 * @author: wddyxd
 * @create: 2025-10-20 21:00
 **/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
