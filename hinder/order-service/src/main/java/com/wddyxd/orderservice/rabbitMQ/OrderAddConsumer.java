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
import com.wddyxd.feign.interceptor.FeignAuthRequestInterceptor;
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

import java.util.ArrayList;
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
    OrderAddingService orderAddingService;

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



        String token = null;
        try {
            // 1. 从消息headers中获取token
            if (message.getMessageProperties().getHeaders().containsKey("token")) {
                token = message.getMessageProperties().getHeader("token").toString();
            }

            // 2. 设置token到Feign拦截器的ThreadLocal中
            FeignAuthRequestInterceptor.setMqToken(token);

            // 3. 核心业务逻辑处理
            orderAddingService.handleOrderAdd(orderMain);

        } finally {
            // 4. 必须清除ThreadLocal中的token，防止内存泄漏
            FeignAuthRequestInterceptor.clearMqToken();
        }

        System.out.printf("消息确认成功！消息ID: %s%n", messageId);


    }




}
