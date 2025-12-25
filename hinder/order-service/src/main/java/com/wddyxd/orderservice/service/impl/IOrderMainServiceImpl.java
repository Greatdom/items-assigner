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
//        传入OrderAddDTO,将token信息和userId比对并检查用户是否可以正常使用,
        // - 然后查看商家是否在开张,
//- 然后查看指向的商品和规格是否存在,没被删除,在上架和在正常使用,
//- 然后查看用户领取的优惠券指向的优惠券是否存在,未删除且在生效而且满足使用条件,
//- 然后查询商品和商品规格,在生成的订单添加商品快照,然后减少商品和规格的库存,
//- 然后在用户领取的优惠券标记优惠券已经使用,然后计算订单总价格和实际价格,
//- 然后生成一份"待付款"的订单状态记录order_status_log,然后生成一份收货地址快照order_address.
//- 要注意超卖问题,分布事务问题,并发性能问题,幂等性问题等.
//- 添加订单备注,比如用了什么优惠券,
//- 然后将该订单存入数据库.
//- 将该订单传入定时任务,如果15分钟没有付款就执行取消订单操作
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
