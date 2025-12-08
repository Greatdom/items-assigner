package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.UserAddressMapper;
import com.wddyxd.userservice.pojo.DTO.UserAddressDTO;
import com.wddyxd.userservice.pojo.entity.UserAddress;
import com.wddyxd.userservice.service.Interface.IUserAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 13:28
 **/
@Service
public class IUserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

    @Override
    public List<UserAddress> List(Long id) {
        if (id == null||id<=0)throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        return this.list(new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getId, id)
                .eq(UserAddress::getIsDeleted, 0));
    }

    @Override
    public void add(UserAddressDTO userAddressDTO) {
//        传入UserAddressDTO,一个用户最多添加5个正常状态的地址簿,
//- 如果将地址设为默认地址,则将之前默认地址设为非默认地址
//- 用redis存储添加的时间戳,过期5秒,redis数据存在期间不能添加数据
    }

    @Override
    public void update(List<UserAddressDTO> userAddressDTOS) {
//        传入List<UserAddressDTO>,注意只将第一个默认地址设为默认地址
    }

    @Override
    public void delete(Long id) {
//       逻辑删除地址簿
    }
}
