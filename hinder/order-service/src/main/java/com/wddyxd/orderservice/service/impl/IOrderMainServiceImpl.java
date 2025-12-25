package com.wddyxd.orderservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.productservice.ProductClient;
import com.wddyxd.feign.clients.userservice.MerchantSupplementClient;
import com.wddyxd.feign.pojo.productservice.ProductDetailVO;
import com.wddyxd.orderservice.mapper.OrderMainMapper;
import com.wddyxd.orderservice.pojo.DTO.OrderDTO;
import com.wddyxd.orderservice.pojo.VO.OrderProfileVO;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:27
 **/
@Service
public class IOrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements IOrderMainService {

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    @Autowired
    private MerchantSupplementClient merchantSupplementClient;

    @Autowired
    private ProductClient productClient;

    @Override
    public void add(OrderDTO orderDTO) {
        //TODO 要注意超卖问题,分布事务问题,并发性能问题,幂等性问题等,将该订单传入定时任务,如果15分钟没有付款就执行取消订单操作
        // 参数校验前用缓存获取商品,优惠券和规格和用户,由此判断参数合法性,如果参数校验成功后就将添加订单信息的接口添加到消息队列里来提高性能

        // 获取当前用户ID
        Long userId = getCurrentUserInfoService.getCurrentUserId();
        //然后查看指向的商品和规格是否存在,没被删除,在上架和在正常使用
        Result<ProductDetailVO> getProductDetailVO = productClient.visit(orderDTO.getProductId());
        //判断结果集合法性
        //判断orderDTO.skuId 是否在结果集内
        //判断couponIds是否全部包含在结果集的List<Coupon>
        //第一次判断quantity是否比sku的stock大
        //然后查看商家是否在开张

        Result<Boolean> getIsValidShop = merchantSupplementClient.getIsValidShop(getProductDetailVO.getData().getUserProfileVO().getId());
        //判断结果集合法性

        //消息队列的异步操作:
        //远程调用商品和规格库存减少接口

        //在生成的订单添加商品快照,然后减少商品和规格的库存

        //然后在用户领取的优惠券标记优惠券已经使用,然后计算订单总价格和实际价格

        //然后生成一份"待付款"的订单状态记录order_status_log,然后生成一份收货地址快照order_address

        //添加订单备注,比如用了什么优惠券,然后将该订单存入数据库


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
