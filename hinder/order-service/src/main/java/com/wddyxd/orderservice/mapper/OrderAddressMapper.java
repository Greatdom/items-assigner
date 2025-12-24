package com.wddyxd.orderservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.orderservice.pojo.entity.OrderAddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:10
 **/
@Mapper
public interface OrderAddressMapper extends BaseMapper<OrderAddress> {
}
