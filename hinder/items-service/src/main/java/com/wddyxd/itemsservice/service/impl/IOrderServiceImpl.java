package com.wddyxd.itemsservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.UserClient;
import com.wddyxd.feign.pojo.User;
import com.wddyxd.itemsservice.mapper.OrderMapper;
import com.wddyxd.itemsservice.pojo.Order;
import com.wddyxd.itemsservice.pojo.dto.OrderDTO;
import com.wddyxd.itemsservice.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:50
 **/

@Service
public class IOrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserClient userClient;

    @Override
    public Result<OrderDTO> getDTOById(Long id) {
        Order order = this.getById(id);
        Result<User> userResult = userClient.FindById(order.getUserId());
        System.out.println(userResult.getCode()+"   "+userResult.getMsg());
        User user = userResult.getData();
        OrderDTO orderDTO = BeanUtil.copyProperties(order, OrderDTO.class);
        orderDTO.setUser(user);
        return Result.success(orderDTO);
    }
}
