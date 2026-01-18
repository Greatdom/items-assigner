package com.wddyxd.orderservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2026-01-18 11:46
 **/

public interface IPaymentOrderStatusLogService extends IService<OrderStatusLog> {

    public String pay(Long id);

    public void rollback(Long id);

}
