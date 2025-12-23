package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import com.wddyxd.userservice.controller.UserAddressController;
import com.wddyxd.userservice.mapper.UserAddressMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.DTO.UserAddressDTO;
import com.wddyxd.userservice.pojo.entity.UserAddress;
import com.wddyxd.userservice.service.Interface.IUserAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 13:28
 **/
@Service
public class IUserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    private static final Logger log = LoggerFactory.getLogger(IUserAddressServiceImpl.class);

    @Override
    public List<UserAddress> List() {
        Long id = getCurrentUserInfoService.getCurrentUserId();
        if (id == null||id<=0)throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        return this.list(new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getId, id)
                .eq(UserAddress::getIsDeleted, 0));
    }

    @Override
    @Transactional
    public void add(UserAddressDTO userAddressDTO) {

        //在redis检查上次添加地址的时间,如果时间间隔小于5秒则不允许添加

        //查找该用户的所有没有被删除的地址的个数,
        // TODO为userId和isDelete字段联合索引,
        // for update在数据库上行锁
        long addressCount = this.count(new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getUserId, userAddressDTO.getUserId())
                .eq(UserAddress::getIsDeleted, 0)
                .last("FOR UPDATE"));
        //如果地址大于5个,则不允许添加
        if (addressCount >= 5) {
            throw new CustomException(ResultCodeEnum.STORAGE_LIMIT_ERROR);
        }
        //如果该地址是默认地址则遍历其他地址并设为非默认地址
        if (userAddressDTO.getIsDefault()) {
            this.update(
                    new LambdaUpdateWrapper<UserAddress>()
                            .set(UserAddress::getIsDefault, false) // 设为非默认
                            .eq(UserAddress::getUserId, userAddressDTO.getUserId())
                            .eq(UserAddress::getIsDeleted, 0)
                            .eq(UserAddress::getIsDefault, true) // 只更原本是默认的记录
            );
        }
        //如果该用户没有地址则让第一个地址为默认地址
        if(addressCount==0)
            userAddressDTO.setIsDefault(true);
        //添加新地址,更新旧地址
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressDTO, userAddress);
        userAddress.setIsDeleted(false); // 兜底，避免传入删除状态
        this.save(userAddress);

        //添加redis时间戳

    }

    @Override
    @Transactional
    public void update(UserAddressDTO userAddressDTO) {
        UserAddress userAddress = baseMapper.selectById(userAddressDTO.getId());
        boolean isDefault = userAddress.getIsDefault();
        BeanUtils.copyProperties(userAddressDTO, userAddress);
        //默认状态不修改
        userAddress.setIsDefault(isDefault);
        this.updateById(userAddress);
    }

    @Override
    public void assign(Long addressId) {
        List<UserAddress> userAddresses = List();
        boolean hasFoundAddress = false;
        for(UserAddress userAddress:userAddresses){
            if(userAddress.getId().equals(addressId)){
                hasFoundAddress = true;
                userAddress.setIsDefault(true);
            }else{
                userAddress.setIsDefault(false);
            }
            if(!hasFoundAddress) {
                log.error("未从此用户找到该地址");
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            }
            this.updateBatchById(userAddresses);
        }
    }

    @Override
    public void delete(Long id) {
        if(id==null||id<=0)throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        UserAddress userAddress = this.getById(id);
        if(userAddress==null||userAddress.getIsDeleted())throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        userAddress.setIsDeleted(true);
        this.updateById(userAddress);
    }
}
