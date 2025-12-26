package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.VO.UserProfileVO;
import com.wddyxd.userservice.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: items-assigner
 * @description: user 数据库接口
 * @author: wddyxd
 * @create: 2025-10-20 21:00
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据单ID查询未被逻辑删除的用户信息
     * @param id 用户ID
     * @return UserProfileVO 单个用户信息
     */
    UserProfileVO selectUserProfileVOById(@Param("id") Long id);

    /**
     * 根据ID数组（批量）查询未被逻辑删除的用户信息
     * @param ids 用户ID数组
     * @return List<UserProfileVO> 用户信息列表
     */
    List<UserProfileVO> selectUserProfileVOListByIds(@Param("ids") Long[] ids);

}
