package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.securityDTO.SecurityUserDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:10
 **/

public interface IUserService extends IService<User> {


    @Transactional
    void addUserAndAssignRole(User user, Long roleId);

    CurrentUserDTO me();

    CurrentUserDTO getUserInfo(Long id);

    Result<?> selectAll(Integer pageNum,Integer pageSize,String search);

    SecurityUserDTO passwordSecurityGetter(String username);

    void add(User user);

    void register(User user);

}
