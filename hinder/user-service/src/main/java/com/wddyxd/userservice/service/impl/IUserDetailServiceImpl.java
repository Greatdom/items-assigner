package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.UserDetailMapper;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import com.wddyxd.userservice.service.Interface.IUserDetailService;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 22:22
 **/
@Service
public class IUserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements IUserDetailService {

    @Override
    public void add(UserDetail userDetail) {
        baseMapper.insert(userDetail);
    }
}
