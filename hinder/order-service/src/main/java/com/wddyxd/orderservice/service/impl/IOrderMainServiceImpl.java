package com.wddyxd.orderservice.service.impl;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.productservice.ProductClient;
import com.wddyxd.feign.clients.productservice.ProductSkuClient;
import com.wddyxd.feign.clients.productservice.UserCouponClient;
import com.wddyxd.feign.clients.userservice.MerchantSupplementClient;
import com.wddyxd.feign.pojo.productservice.Coupon;
import com.wddyxd.feign.pojo.productservice.ProductDetailVO;
import com.wddyxd.feign.pojo.productservice.ProductSkuVO;
import com.wddyxd.orderservice.mapper.OrderMainMapper;
import com.wddyxd.orderservice.pojo.DTO.OrderDTO;
import com.wddyxd.orderservice.pojo.VO.OrderProfileVO;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;
import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import com.wddyxd.orderservice.service.Interface.IOrderStatusLogService;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ProductSkuClient productSkuClient;

    @Autowired
    private UserCouponClient userCouponClient;

    @Autowired
    private IOrderStatusLogService orderStatusLogService;

    @Override
    public void add(OrderDTO orderDTO) {
        //TODO 要注意超卖问题,分布事务问题,并发性能问题,幂等性问题等,将该订单传入定时任务,如果15分钟没有付款就执行取消订单操作
        // 参数校验前用缓存获取商品,优惠券和规格和用户,由此判断参数合法性,如果参数校验成功后就将添加订单信息的接口添加到消息队列里来提高性能

        // 获取当前用户ID
        Long userId = getCurrentUserInfoService.getCurrentUserId();
        //然后查看指向的商品和规格是否存在,没被删除,在上架和在正常使用
        Result<ProductDetailVO> getProductDetailVO = productClient.visit(orderDTO.getProductId());
        //判断结果集合法性
        OrderMain orderMain = new OrderMain();
        //判断orderDTO.skuId 是否在结果集内
        boolean isSkuExist = false;
        for(ProductSkuVO productSkuVO:getProductDetailVO.getData().getProductSkuVO())
            if(productSkuVO.getId().equals(orderDTO.getSkuId())){
                orderMain.setSkuSpecs(productSkuVO.getSpecs());
                isSkuExist = true;
                break;
            }
        if(!isSkuExist) {
            log.error("商品规格不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //设置订单ID
        orderMain.setId(IdWorker.getId());
        //设置用户ID
        orderMain.setUserId(userId);
        //设置商品ID
        orderMain.setProductId(orderDTO.getProductId());
        //设置商品规格ID
        orderMain.setSkuId(orderDTO.getSkuId());
        //设置商品数量
        orderMain.setQuantity(orderDTO.getQuantity());
        //设置商品名称
        orderMain.setProductName(getProductDetailVO.getData().getProductProfileVO().getProductName());
        //判断couponIds是否全部包含在结果集的List<Coupon>
        Map couponMap = new HashMap<Long,Void>();
        for (Long couponId:orderDTO.getCouponIds()) couponMap.put(couponId,null);
        int couponCount = 0;
        for(Coupon coupon:getProductDetailVO.getData().getCoupon())
            if(couponMap.containsKey(coupon.getId())) couponCount++;
        if(couponCount!=orderDTO.getCouponIds().length){
            log.error("不合法的优惠券列表");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //第一次判断quantity是否比sku的stock大

        //然后查看商家是否在开张
        Result<Boolean> getIsValidShop = merchantSupplementClient.getIsValidShop(getProductDetailVO.getData().getUserProfileVO().getId());
        //判断结果集合法性

        //TODO 消息队列的异步操作:
        //远程调用商品和规格库存减少接口,远程接口第二次判断quantity是否比sku的stock大
        Result<Void> getProductSkuConsume= productSkuClient.updateConsume(orderDTO.getSkuId(), orderDTO.getQuantity());
        //判断结果集合法性
        //然后在用户领取的优惠券标记优惠券已经使用,然后计算订单总价格和实际价格
        Result<List<Long>> getUserCouponConsume = userCouponClient.consume(orderDTO.getCouponIds(), orderDTO.getId());
        //判断结果集合法性
        //计算totalPrice和payPrice
        orderMain.setTotalPrice(getProductDetailVO.getData().getProductProfileVO().getPrice()
                .multiply(new BigDecimal(orderDTO.getQuantity())));
        //TODO 要根据优惠券的使用情况和性质计算payPrice
        orderMain.setPayPrice(orderMain.getTotalPrice());
        //添加remark,如果订单发起成功则记录优惠券使用情况,否则则记录订单发起失败
        orderMain.setRemark("如果订单发起成功则记录优惠券使用情况,否则则记录订单发起失败");
        //成功则设置status=0,否则status=5
        orderMain.setStatus(0);
        //设置payMethod=0
        orderMain.setPayMethod(0);

        //然后生成一份"待付款"的订单状态记录order_status_log,然后生成一份收货地址快照order_address
        OrderStatusLog orderStatusLog = new OrderStatusLog();
        orderStatusLog.setOrderId(orderMain.getId());
        orderStatusLog.setStatus(0);
        orderStatusLog.setOperatorId(orderMain.getUserId());
        orderStatusLog.setOperateTime(new Date());
        orderStatusLog.setRemark("订单待付款");
        orderStatusLogService.save(orderStatusLog);
        //将该订单存入数据库
        baseMapper.insert(orderMain);

        //TODO 异步操作完成后要向前端返回信息使前端跳转到支付页面,有两个方案
//        方案 1：前端轮询
//        前端发起请求，后端生成唯一任务 ID并存入redis，校验参数后将消息发送到 RabbitMQ，同时返回任务 ID 给前端
//        消费者异步消费消息，处理完业务后，将处理结果与任务 ID 关联，更新到redis（标记任务状态为 “处理完成”）
//        前端拿到任务 ID 后，通过定时轮询（如每 3 秒调用一个查询接口），传入任务 ID 查询任务状态和处理结果
//        当查询到任务状态为 “处理完成” 时，前端获取结果并停止轮询
//        实现简单,实时性差
//        方案 2：webSocket
//        后端发送消息并缓存连接：后端接收前端请求后，将业务参数 + 前端唯一标识发送到 RabbitMQ，同时将 WebSocket/SSE 连接缓存到本地（如 Map、Redis）；
//        消费者处理并主动推送结果：消费者处理完业务后，通过前端唯一标识找到对应的缓存连接，直接将处理结果推送给前端
//        前端接收结果并关闭连接：前端实时接收推送的结果，处理完成后可主动关闭长连接
//        实现复杂,实时性高

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
