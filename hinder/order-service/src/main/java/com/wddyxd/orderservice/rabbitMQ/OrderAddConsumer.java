package com.wddyxd.orderservice.rabbitMQ;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.rabbitmq.client.Channel;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.productservice.ProductSkuClient;
import com.wddyxd.feign.clients.productservice.UserCouponClient;
import com.wddyxd.feign.clients.userservice.UserAddressClient;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserAddress;
import com.wddyxd.orderservice.mapper.OrderAddressMapper;
import com.wddyxd.orderservice.mapper.OrderMainMapper;
import com.wddyxd.orderservice.pojo.entity.OrderAddress;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;
import com.wddyxd.orderservice.service.Interface.ICommonOrderStatusLogService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2026-01-05 20:53
 **/
@Component
public class OrderAddConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderAddConsumer.class);

    @Autowired
    private ICommonOrderStatusLogService commonOrderStatusLogService;
    @Autowired
    private OrderAddressMapper orderAddressMapper;
    @Autowired
    private OrderMainMapper orderMainMapper;
    @Autowired
    private ProductSkuClient productSkuClient;
    @Autowired
    private UserCouponClient userCouponClient;
    @Autowired
    private UserAddressClient userAddressClient;


    @RabbitListener(queuesToDeclare = @Queue(
            name = "${rabbitmq.queue.compress:order-add-queue}", // 保留配置读取+默认值
            durable = "true",
            exclusive = "false",
            autoDelete = "false"
    ))
    public void consumeOrderAdd(
            String messageBody,
            @Header(AmqpHeaders.MESSAGE_ID) String messageId,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
            Message message
    ) {

        // 1. 打印接收到的消息基本信息
        System.out.printf("收到消息！消息ID: %s, 路由键: %s%n",
                messageId, message.getMessageProperties().getReceivedRoutingKey());

        // 2. 解析JSON消息体为Student对象
        OrderMain orderMain = JSON.parseObject(messageBody, OrderMain.class);
        System.out.println("解析后的订单ID:"+
                orderMain.getId());

        // 3. 核心业务逻辑处理（这里替换为你的实际业务）
        handleOrderAdd(orderMain);

        System.out.printf("消息确认成功！消息ID: %s%n", messageId);


    }



    @GlobalTransactional
    public void handleOrderAdd( OrderMain orderMain) {
        //远程调用商品和规格库存减少接口,远程接口第二次判断quantity是否比sku的stock大
        Result<Void> getProductSkuConsume= productSkuClient.updateConsume(orderMain.getSkuId(), orderMain.getQuantity());
        if(getProductSkuConsume==null||getProductSkuConsume.getCode()!=200){
            log.error("商品规格库存不足");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //然后在用户领取的优惠券标记优惠券已经使用,然后计算订单总价格和实际价格
        Result<List<Long>> getUserCouponConsume = userCouponClient.consume(orderMain.getCouponIds(), orderMain.getId());
        if(getUserCouponConsume==null||getUserCouponConsume.getCode()!=200){
            log.error("优惠券使用失败");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //计算payPrice
        //TODO 要根据优惠券的使用情况和性质计算payPrice
        orderMain.setPayPrice(orderMain.getTotalPrice());
        //添加remark,如果订单发起成功则记录优惠券使用情况,否则则记录订单发起失败
        orderMain.setRemark("消费优惠券: "+getUserCouponConsume.getData().toString());
        //成功则设置status=0
        orderMain.setStatus(0);
        //设置payMethod=0
        orderMain.setPayMethod(0);

        //然后生成一份"待付款"的订单状态记录order_status_log,然后生成一份收货地址快照order_address
        OrderStatusLog orderStatusLog = new OrderStatusLog();
        orderStatusLog.setOrderId(orderMain.getId());
        orderStatusLog.setStatus(0);
        orderStatusLog.setOperatorId(orderMain.getBuyerId());
        orderStatusLog.setOperateTime(new Date());
        orderStatusLog.setRemark("订单待付款");
        commonOrderStatusLogService.save(orderStatusLog);

        //然后存储收货地址快照
        Result<UserAddress> userAddress = userAddressClient.getDefault(orderMain.getBuyerId());
        if(userAddress==null||userAddress.getCode()!=200||userAddress.getData()==null){
            log.error("获取用户地址失败");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        OrderAddress orderAddress = new OrderAddress();
        BeanUtil.copyProperties(userAddress.getData(),orderAddress);
        orderAddress.setOrderId(orderMain.getId());
        orderAddress.setOrderId(IdWorker.getId());
        orderAddress.setUpdateTime(new Date());
        orderAddress.setCreateTime(new Date());
        orderAddressMapper.insert(orderAddress);

        //将该订单存入数据库
        orderMainMapper.insert(orderMain);

        //TODO 异步操作完成后要向前端返回信息使前端跳转到支付页面,有两个方案,但得在开发前端代码后考虑
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

}
