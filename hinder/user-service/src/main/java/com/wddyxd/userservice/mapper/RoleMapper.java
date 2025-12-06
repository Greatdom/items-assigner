package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.VO.RoleVO;
import com.wddyxd.userservice.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: items-assigner
 * @description: role 数据库接口
 * @author: wddyxd
 * @create: 2025-10-20 20:58
 **/

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    public RoleVO detail(Long id);

}
