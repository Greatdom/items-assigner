package com.wddyxd.orderservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.orderservice.mapper.OrderStatusLogMapper;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;
import com.wddyxd.orderservice.service.Interface.IOrderStatusLogService;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:30
 **/
@Service
public class IOrderStatusLogServiceImpl extends ServiceImpl<OrderStatusLogMapper, OrderStatusLog> implements IOrderStatusLogService {


    @Override
    public OrderStatusLog list(Long id) {
        return null;
    }

    @Override
    public void pay(Long id) {

    }

    @Override
    public void ship(Long id) {

    }

    @Override
    public void receive(Long id) {

    }

    @Override
    public void cancel(Long id) {

    }

    @Override
    public void rollback(Long id) {

    }
}
