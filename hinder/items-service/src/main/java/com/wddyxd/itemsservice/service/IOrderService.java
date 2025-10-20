package com.wddyxd.itemsservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.utils.Result;
import com.wddyxd.itemsservice.pojo.Order;
import com.wddyxd.itemsservice.pojo.dto.OrderDTO;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:50
 **/

public interface IOrderService extends IService<Order> {

    Result<OrderDTO> getDTOById(Long id);

}
