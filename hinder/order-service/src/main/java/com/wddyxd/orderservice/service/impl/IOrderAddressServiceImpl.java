package com.wddyxd.orderservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.orderservice.mapper.OrderAddressMapper;
import com.wddyxd.orderservice.pojo.entity.OrderAddress;
import com.wddyxd.orderservice.service.Interface.IOrderAddressService;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:31
 **/
@Service
public class IOrderAddressServiceImpl extends ServiceImpl<OrderAddressMapper, OrderAddress> implements IOrderAddressService {

    @Override
    public void add() {

    }

    @Override
    public OrderAddress detail(Long id) {
        return null;
    }

    @Override
    public void update(Long id) {

    }
}
