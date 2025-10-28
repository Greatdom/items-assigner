package com.wddyxd.userservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.utils.Result;
import com.wddyxd.security.pojo.SecurityUser;
import com.wddyxd.userservice.pojo.User;
import com.wddyxd.userservice.pojo.dto.CurrentUserDTO;
import com.wddyxd.userservice.pojo.securityDTO.SecurityUserDTO;

import java.util.List;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:10
 **/

public interface IUserService extends IService<User> {

    public User selectByUsername(String username);

    CurrentUserDTO me();

    CurrentUserDTO getUserInfo(Long id);

    Result<?> selectAll(Integer pageNum,Integer pageSize,String search);

    SecurityUserDTO passwordSecurityGetter(String username);
}
