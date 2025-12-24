package com.wddyxd.orderservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.orderservice.pojo.DTO.OrderDTO;
import com.wddyxd.orderservice.pojo.VO.OrderProfileVO;
import com.wddyxd.orderservice.pojo.entity.OrderMain;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:11
 **/

public interface IOrderMainService extends IService<OrderMain> {

    public void add(OrderDTO orderDTO);

    public OrderProfileVO listUser(SearchDTO searchDTO);

    public OrderProfileVO listMerchant(SearchDTO searchDTO);

    public OrderProfileVO listAdmin(SearchDTO searchDTO);

    public OrderProfileVO detail(Long id);

    public void update(OrderDTO orderDTO);

}
