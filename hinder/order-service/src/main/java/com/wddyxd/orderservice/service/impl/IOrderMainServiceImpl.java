package com.wddyxd.orderservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.orderservice.mapper.OrderMainMapper;
import com.wddyxd.orderservice.pojo.DTO.OrderDTO;
import com.wddyxd.orderservice.pojo.VO.OrderProfileVO;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:27
 **/
@Service
public class IOrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements IOrderMainService {

    @Override
    public void add(OrderDTO orderDTO) {

    }

    @Override
    public OrderProfileVO listUser(SearchDTO searchDTO) {
        return null;
    }

    @Override
    public OrderProfileVO listMerchant(SearchDTO searchDTO) {
        return null;
    }

    @Override
    public OrderProfileVO listAdmin(SearchDTO searchDTO) {
        return null;
    }

    @Override
    public OrderProfileVO detail(Long id) {
        return null;
    }

    @Override
    public void update(OrderDTO orderDTO) {

    }
}
