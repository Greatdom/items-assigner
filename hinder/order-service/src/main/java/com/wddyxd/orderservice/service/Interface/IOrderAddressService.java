package com.wddyxd.orderservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.orderservice.pojo.entity.OrderAddress;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:12
 **/

public interface IOrderAddressService extends IService<OrderAddress> {

    public void add();

    public OrderAddress detail(Long id);

    public void update(Long id);

}
