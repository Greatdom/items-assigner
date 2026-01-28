package com.wddyxd.orderservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.productservice.ProductClient;
import com.wddyxd.feign.clients.userservice.MerchantSupplementClient;
import com.wddyxd.feign.clients.userservice.UserClient;
import com.wddyxd.feign.pojo.productservice.Coupon;
import com.wddyxd.feign.pojo.productservice.ProductDetailVO;
import com.wddyxd.feign.pojo.productservice.ProductProfileVO;
import com.wddyxd.feign.pojo.productservice.ProductSkuVO;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserProfileVO;
import com.wddyxd.orderservice.mapper.OrderAddressMapper;
import com.wddyxd.orderservice.mapper.OrderMainMapper;
import com.wddyxd.orderservice.pojo.DTO.OrderDTO;
import com.wddyxd.orderservice.pojo.VO.OrderDetailVO;
import com.wddyxd.orderservice.pojo.VO.OrderProfileVO;
import com.wddyxd.orderservice.pojo.entity.OrderAddress;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:27
 **/
@Service
public class IOrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements IOrderMainService {

    private static final Logger log = LoggerFactory.getLogger(IOrderMainServiceImpl.class);

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    @Autowired
    private MerchantSupplementClient merchantSupplementClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderAddressMapper orderAddressMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void add(OrderDTO orderDTO) {
        //TODO 要注意超卖问题,分布事务问题,并发性能问题,幂等性问题等,将该订单传入定时任务,如果15分钟没有付款就执行取消订单操作
        // 参数校验前用缓存获取商品,优惠券和规格和用户,由此判断参数合法性,如果参数校验成功后就将添加订单信息的接口添加到消息队列里来提高性能

        // 获取当前用户ID
        Long userId = getCurrentUserInfoService.getCurrentUserId();


        //然后查看指向的商品和规格是否存在,没被删除,在上架和在正常使用
        Result<ProductDetailVO> getProductDetailVO = productClient.visit(orderDTO.getProductId());
        if(getProductDetailVO== null||getProductDetailVO.getCode()!=200||getProductDetailVO.getData()==null){
            log.error("商品不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }

        OrderMain orderMain = new OrderMain();
        //判断orderDTO.skuId 是否在结果集内
        boolean isSkuExist = false;
        for(ProductSkuVO productSkuVO:getProductDetailVO.getData().getProductSkuVO())
            if(productSkuVO.getId().equals(orderDTO.getSkuId())){
                //第一次判断quantity是否比sku的stock大
                if(orderDTO.getQuantity()>productSkuVO.getStock()){
                    log.error("商品库存不足");
                    throw new CustomException(ResultCodeEnum.PARAM_ERROR);
                }
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
        //设置买家和卖家的ID
        orderMain.setBuyerId(userId);
        orderMain.setMerchantId(getProductDetailVO.getData().getProductProfileVO().getUserId());
        //设置商品ID
        orderMain.setProductId(orderDTO.getProductId());
        //设置商品规格ID
        orderMain.setSkuId(orderDTO.getSkuId());
        //设置商品数量
        orderMain.setQuantity(orderDTO.getQuantity());
        //设置商品名称
        orderMain.setProductName(getProductDetailVO.getData().getProductProfileVO().getProductName());
        //设置totalPrice
        orderMain.setTotalPrice(getProductDetailVO.getData().getProductProfileVO().getPrice()
                .multiply(new BigDecimal(orderMain.getQuantity())));
        //设置待处理的couponIds
        orderMain.setCouponIds(orderDTO.getCouponIds());

        //判断couponIds是否全部包含在结果集的List<Coupon>
        Map<Long,Void> couponMap = new HashMap<>();
        for (Long couponId:orderDTO.getCouponIds()) couponMap.put(couponId,null);
        int couponCount = 0;
        for(Coupon coupon:getProductDetailVO.getData().getCoupon())
            if(couponMap.containsKey(coupon.getId())) couponCount++;
        if(couponCount!=orderDTO.getCouponIds().length){
            log.error("不合法的优惠券列表");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }

        //然后查看商家是否在开张
        Result<Boolean> getIsValidShop = merchantSupplementClient.getIsValidShop(getProductDetailVO.getData().getUserProfileVO().getId());
        if(getIsValidShop==null||getIsValidShop.getCode()!=200||getIsValidShop.getData()==false){
            log.error("未开店的商家");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }




        // 1. 生成ID
        String messageId = UUID.randomUUID().toString(); // 简化UUID格式

        String token = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            token = request.getHeader("token");
        }

        // 2. 构建消息体
        byte[] messageBody = JSON.toJSONString(orderMain).getBytes();
        MessageProperties properties = new MessageProperties();
        properties.setMessageId(messageId); // 为消息本身设置唯一ID（关键）
        if (token != null) {
            properties.setHeader("token", token);
        }
        properties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        properties.setDeliveryMode(MessageProperties.DEFAULT_DELIVERY_MODE); // 持久化
        //TODO 如果是分布式事务要设置消息本身的唯一ID
        Message message = MessageBuilder.withBody(messageBody)
                .andProperties(properties)
                .build();

        // 5. 构建CorrelationData用于发送确认回调
        CorrelationData correlationData = new CorrelationData(messageId);
        CompletableFuture<CorrelationData.Confirm> future = correlationData.getFuture();
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.printf("消息发送回调异常！MessageID: %s, CorrelationID: %s, 异常: %s%n",
                        messageId, messageId, ex.getMessage());
                return;
            }

            if (result != null) {
                if (result.isAck()) {
                    System.out.printf("消息发送成功！MessageID: %s, CorrelationID: %s, 订单ID: %s%n",
                            messageId, messageId, orderMain.getId());
                } else {
                    System.err.printf("消息发送失败！MessageID: %s, CorrelationID: %s, 原因: %s%n",
                            messageId, messageId, result.getReason() != null ? result.getReason() : "未知");
                }
            } else {
                System.err.printf("消息状态未知！MessageID: %s, CorrelationID: %s%n",
                        messageId, messageId);
            }
        });


        //开始异步添加订单
        rabbitTemplate.convertAndSend(CommonConstant.ORDER_ADD_QUEUE,message,correlationData);

    }




    @Override
    public Page<OrderProfileVO> listUser(SearchDTO searchDTO) {
        Page<OrderProfileVO> page = baseMapper.listUser(
                new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), searchDTO.getSearch());
        List<Long> merchantIds = page.getRecords()
                .stream()
                .map(OrderProfileVO::getMerchantId)
                .toList();
        Result<HashMap<Long,UserProfileVO>> getUserProfileVOS = userClient.profiles(merchantIds.toArray(Long[]::new));
        if(getUserProfileVOS==null||getUserProfileVOS.getData()==null||getUserProfileVOS.getCode()!=200){
            log.error("获取用户信息失败");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        HashMap<Long,UserProfileVO> userProfileVOS = getUserProfileVOS.getData();
        //判断结果集合法性
        for(OrderProfileVO orderProfileVO:page.getRecords()){
            if(userProfileVOS.containsKey(orderProfileVO.getMerchantId())){
                com.wddyxd.orderservice.pojo.VO.UserProfileVO userProfileVO = new com.wddyxd.orderservice.pojo.VO.UserProfileVO();
                BeanUtil.copyProperties(userProfileVOS.get(orderProfileVO.getMerchantId()),userProfileVO);
                orderProfileVO.setUserProfileVO(userProfileVO);
            }
        }
        return page;
    }

    @Override
    public Page<OrderProfileVO> listMerchant(SearchDTO searchDTO) {
        Page<OrderProfileVO> page = baseMapper.listMerchant(
                new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), searchDTO.getSearch());
        List<Long> buyerIds = page.getRecords()
                .stream()
                .map(OrderProfileVO::getBuyerId)
                .toList();
        Result<HashMap<Long,UserProfileVO>> getUserProfileVOS = userClient.profiles(buyerIds.toArray(Long[]::new));
        if(getUserProfileVOS==null||getUserProfileVOS.getData()==null||getUserProfileVOS.getCode()!=200){
            log.error("获取用户信息失败");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        HashMap<Long,UserProfileVO> userProfileVOS = getUserProfileVOS.getData();
        //判断结果集合法性
        for(OrderProfileVO orderProfileVO:page.getRecords()){
            if(userProfileVOS.containsKey(orderProfileVO.getBuyerId())){
                com.wddyxd.orderservice.pojo.VO.UserProfileVO userProfileVO = new com.wddyxd.orderservice.pojo.VO.UserProfileVO();
                BeanUtil.copyProperties(userProfileVOS.get(orderProfileVO.getBuyerId()),userProfileVO);
                orderProfileVO.setUserProfileVO(userProfileVO);
            }
        }
        return page;
    }

    @Override
    public Page<OrderProfileVO> listAdmin(SearchDTO searchDTO) {
        Page<OrderProfileVO> page = baseMapper.listMerchant(
                new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), searchDTO.getSearch());
        List<Long> buyerIds = page.getRecords()
                .stream()
                .map(OrderProfileVO::getBuyerId)
                .toList();
        Result<HashMap<Long,UserProfileVO>> getUserProfileVOS = userClient.profiles(buyerIds.toArray(Long[]::new));
        if(getUserProfileVOS==null||getUserProfileVOS.getData()==null||getUserProfileVOS.getCode()!=200){
            log.error("获取用户信息失败");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        HashMap<Long,UserProfileVO> userProfileVOS = getUserProfileVOS.getData();
        //判断结果集合法性
        for(OrderProfileVO orderProfileVO:page.getRecords()){
            if(userProfileVOS.containsKey(orderProfileVO.getBuyerId())){
                com.wddyxd.orderservice.pojo.VO.UserProfileVO userProfileVO = new com.wddyxd.orderservice.pojo.VO.UserProfileVO();
                BeanUtil.copyProperties(userProfileVOS.get(orderProfileVO.getBuyerId()),userProfileVO);
                orderProfileVO.setUserProfileVO(userProfileVO);
            }
        }
        return page;
    }

    @Override
    public OrderDetailVO detail(Long id) {
        //获取OrderMain
        OrderMain orderMain = baseMapper.selectById(id);
        //获取ProductProfileVO
        Result<ProductProfileVO> getProductProfileVO = productClient.get(orderMain.getProductId());
        if(getProductProfileVO==null||getProductProfileVO.getData()==null||getProductProfileVO.getCode()!=200){
            log.error("获取商品信息失败");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        com.wddyxd.orderservice.pojo.VO.ProductProfileVO productProfileVO = new com.wddyxd.orderservice.pojo.VO.ProductProfileVO();
        BeanUtil.copyProperties(getProductProfileVO.getData(),productProfileVO);
        //获取UserProfileVO
        List<Long> counterPartIds = new ArrayList<>();
        counterPartIds.add(orderMain.getBuyerId());
        if(!Objects.equals(orderMain.getBuyerId(), orderMain.getMerchantId()))
            counterPartIds.add(orderMain.getMerchantId());
        Result<HashMap<Long,UserProfileVO>> getUserProfileVOS = userClient.profiles(counterPartIds.toArray(Long[]::new));
        if(getUserProfileVOS==null||getUserProfileVOS.getData()==null||getUserProfileVOS.getCode()!=200){
            log.error("获取用户信息失败");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        HashMap<String,com.wddyxd.orderservice.pojo.VO.UserProfileVO> resultUserProfileVOS = new HashMap<>();
        com.wddyxd.orderservice.pojo.VO.UserProfileVO buyer = new com.wddyxd.orderservice.pojo.VO.UserProfileVO();
        com.wddyxd.orderservice.pojo.VO.UserProfileVO merchant = new com.wddyxd.orderservice.pojo.VO.UserProfileVO();
        BeanUtil.copyProperties(getUserProfileVOS.getData().get(orderMain.getBuyerId()),buyer);
        BeanUtil.copyProperties(getUserProfileVOS.getData().get(orderMain.getMerchantId()),merchant);
        resultUserProfileVOS.put(OrderDetailVO.userProfileVOMapKeys.buyer.getKey(),buyer);
        resultUserProfileVOS.put(OrderDetailVO.userProfileVOMapKeys.merchant.getKey(),merchant);
        //获取OrderAddress
        OrderAddress orderAddress = orderAddressMapper.selectOne(new LambdaQueryWrapper<OrderAddress>()
                .eq(OrderAddress::getOrderId, id)
        );
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setOrderMain(orderMain);
        orderDetailVO.setOrderAddress(orderAddress);
        orderDetailVO.setUserProfileVOMap(resultUserProfileVOS);
        orderDetailVO.setProductProfileVO(productProfileVO);
        return orderDetailVO;
    }


}
