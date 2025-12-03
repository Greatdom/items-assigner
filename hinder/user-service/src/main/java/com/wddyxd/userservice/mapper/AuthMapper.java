package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 16:09
 **/

@Mapper
public interface AuthMapper extends BaseMapper<User> {

    CurrentUserDTO getCurrentUserById(Long id);

}
