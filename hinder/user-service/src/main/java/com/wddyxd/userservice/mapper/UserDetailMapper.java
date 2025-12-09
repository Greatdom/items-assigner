package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 22:21
 **/

@Mapper
public interface UserDetailMapper extends BaseMapper<UserDetail> {

    @Select("select * from user_detail where user_id = #{userId}")
    public UserDetail selectByUserId(Long userId);

}
