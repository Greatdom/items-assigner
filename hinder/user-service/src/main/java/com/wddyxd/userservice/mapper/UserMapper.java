package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: items-assigner
 * @description: user 数据库接口
 * @author: wddyxd
 * @create: 2025-10-20 21:00
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
