package com.wddyxd.orderservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.userservice.UserAddressClient;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserAddress;
import com.wddyxd.orderservice.controller.OrderAddressController;
import com.wddyxd.orderservice.mapper.OrderAddressMapper;
import com.wddyxd.orderservice.pojo.entity.OrderAddress;
import com.wddyxd.orderservice.service.Interface.IOrderAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:31
 **/
@Service
public class IOrderAddressServiceImpl extends ServiceImpl<OrderAddressMapper, OrderAddress> implements IOrderAddressService {

    private static final Logger log = LoggerFactory.getLogger(IOrderAddressServiceImpl.class);

    @Autowired
    private UserAddressClient userAddressClient;

    @Override
    public void update(Long userAddressId,Long orderAddressId) {
        Result<UserAddress> getUserAddress = userAddressClient.getSpecial(userAddressId);
        if(getUserAddress == null || getUserAddress.getCode() != 200 || getUserAddress.getData() == null){
            log.info("用户地址不存在");
            throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
        }
        OrderAddress orderAddress = baseMapper.selectById(orderAddressId);
        if(orderAddress== null||orderAddress.getIsDeleted()){
            log.info("订单地址不存在");
            throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
        }
        BeanUtil.copyProperties(getUserAddress.getData(),orderAddress);
        baseMapper.updateById(orderAddress);
    }
}
