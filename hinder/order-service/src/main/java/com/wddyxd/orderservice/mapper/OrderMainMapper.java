package com.wddyxd.orderservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.orderservice.pojo.VO.OrderProfileVO;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:08
 **/

@Mapper
public interface OrderMainMapper extends BaseMapper<OrderMain> {

    public Page<OrderProfileVO> listUser(Page<OrderProfileVO> page, String search);

    public Page<OrderProfileVO> listMerchant(Page<OrderProfileVO> page, String search);

    public Page<OrderProfileVO> listAdmin(Page<OrderProfileVO> page, String search);

}
