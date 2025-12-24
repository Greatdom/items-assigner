package com.wddyxd.orderservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:12
 **/

public interface IOrderStatusLogService extends IService<OrderStatusLog> {

    public OrderStatusLog list(Long id);

    public void pay(Long id);

    public void ship(Long id);

    public void receive(Long id);

    public void cancel(Long id);

    public void rollback(Long id);

}
