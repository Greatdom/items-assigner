package com.wddyxd.orderservice.rabbitMQ;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wddyxd.common.constant.CommonConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

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
import io.seata.core.context.RootContext;
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

import java.io.IOException;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2026-01-05 20:53
 **/


@Component
public class OrderAddConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderAddConsumer.class);

    // 最大重试次数
    private static final int MAX_RETRY_COUNT = 2;

    @Autowired
    private OrderAddingService orderAddingService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 读取队列名称（与声明的队列保持一致）
    @Value("${rabbitmq.queue.compress:order-add-queue}")
    private String orderAddQueue;

    @RabbitListener(queuesToDeclare = @Queue(
            name = "${rabbitmq.queue.compress:order-add-queue}",
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
    ) throws IOException {



        // 打印接收到的消息基本信息
        log.info("收到消息！消息ID: {}, 路由键: {}",
                messageId, message.getMessageProperties().getReceivedRoutingKey());

        // 初始化重试次数
        int retryCount = 0;
        Map<String, Object> headers = message.getMessageProperties().getHeaders();

        // 从headers中获取已有的重试次数
        if (headers.containsKey("x-retry-count")) {
            try {
                retryCount = Integer.parseInt(headers.get("x-retry-count").toString());
            } catch (NumberFormatException e) {
                log.warn("解析重试次数失败，重置为0，消息ID: {}", messageId);
                retryCount = 0;
            }
        }

        String token = null;
        try {
            // 1. 从消息headers中获取token
            if (headers.containsKey("token")) {
                token = headers.get("token").toString();
            }

            // 2. 设置token到Feign拦截器的ThreadLocal中
            FeignAuthRequestInterceptor.setMqToken(token);

            // 3. 核心业务逻辑处理
            OrderMain orderMain = JSON.parseObject(messageBody, OrderMain.class);
            log.info("解析后的订单ID: {}", orderMain.getId());

            orderAddingService.handleOrderAdd(orderMain);

            // 消费成功，手动确认
            channel.basicAck(deliveryTag, false);
            log.info("消息确认成功！消息ID: {}", messageId);

        } catch (Exception e) {
            log.error("消费失败，消息ID: {}, 重试次数: {}", messageId, retryCount, e);

            try {
                // 4. 判断是否需要重试
                if (retryCount < MAX_RETRY_COUNT &&
                        !(e instanceof CustomException && Objects.equals(((CustomException) e).getCode(), ResultCodeEnum.STOCK_NOT_ENOUGH_ERROR.getCode()))) {
                    // 4.1 重试次数+1
                    int newRetryCount = retryCount + 1;
                    log.info("准备重试消息，消息ID: {}, 新重试次数: {}", messageId, newRetryCount);

                    // 4.2 构建新的消息属性，保留原属性并更新重试次数
                    MessageProperties newMsgProps = message.getMessageProperties();
                    newMsgProps.getHeaders().put("x-retry-count", newRetryCount);
                    // 设置投递模式为持久化（与原队列一致）
                    newMsgProps.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

                    // 4.3 重新投递到原队列末尾（basicPublish到原队列，不使用basicNack的requeue=true）
                    rabbitTemplate.convertAndSend(CommonConstant.ORDER_ADD_QUEUE,message);
//                    channel.basicPublish("", orderAddQueue, newMsgProps, messageBody.getBytes());
                    log.info("消息重试投递成功，消息ID: {}, 重试次数: {}", messageId, newRetryCount);

                    // 4.4 确认原消息已处理（拒绝并丢弃，避免重复）
                    channel.basicNack(deliveryTag, false, false);

                } else {
                    // 5. 重试次数达到上限，路由到错误交换机
                    log.warn("消息重试次数达到上限，路由到错误交换机，消息ID: {}", messageId);

                    // 5.1 构建错误消息属性，记录失败原因
                    MessageProperties errorMsgProps = message.getMessageProperties();
                    errorMsgProps.getHeaders().put("x-fail-reason", e.getMessage());
                    errorMsgProps.getHeaders().put("x-retry-max", MAX_RETRY_COUNT);

                    // 5.2 发送到错误交换机
                    rabbitTemplate.convertAndSend("error.direct", "error", messageBody, msg -> {
                        // 获取错误消息的属性对象
                        MessageProperties props = msg.getMessageProperties();
                        // 1. 设置消息为持久化，避免丢失
                        props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

                        // 2. 添加异常相关信息到headers
                        // 异常具体原因（截断过长的信息，避免header过大）
                        String errorMsg = e.getMessage() != null ? e.getMessage() : "未知异常";
                        props.setHeader("x-fail-reason", errorMsg.length() > 2000 ? errorMsg.substring(0, 2000) : errorMsg);
                        // 异常类型（如NullPointerException、BusinessException等）
                        props.setHeader("x-exception-type", e.getClass().getName());
                        // 最大重试次数
                        props.setHeader("x-retry-max", MAX_RETRY_COUNT);
                        // 消息ID，便于关联原消息
                        props.setHeader("x-origin-message-id", messageId);
                        // 订单ID（如果解析成功的话，方便定位业务数据）
                        try {
                            OrderMain orderMain = JSON.parseObject(messageBody, OrderMain.class);
                            props.setHeader("x-order-id", orderMain.getId());
                        } catch (Exception parseEx) {
                            props.setHeader("x-order-id", "解析失败：" + parseEx.getMessage());
                        }
                        // 失败时间戳
                        props.setHeader("x-fail-timestamp", System.currentTimeMillis());

                        return msg;
                    });

                    // 5.3 确认原消息已处理
                    channel.basicNack(deliveryTag, false, false);
                }
            } catch (IOException ex) {
                log.error("处理失败消息时发生异常，消息ID: {}", messageId, ex);
                // 极端情况：投递错误交换机失败，触发死信（需提前配置队列死信）
                channel.basicNack(deliveryTag, false, false);
            }
        } finally {
            // 6. 必须清除ThreadLocal中的token，防止内存泄漏
            FeignAuthRequestInterceptor.clearMqToken();
            RootContext.unbind();
        }
    }


}

//@Component
//public class OrderAddConsumer {
//
//    private static final Logger log = LoggerFactory.getLogger(OrderAddConsumer.class);
//
//    @Autowired
//    OrderAddingService orderAddingService;
//
//    @RabbitListener(queuesToDeclare = @Queue(
//            name = "${rabbitmq.queue.compress:order-add-queue}", // 保留配置读取+默认值
//            durable = "true",
//            exclusive = "false",
//            autoDelete = "false"
//    ))
//    public void consumeOrderAdd(
//            String messageBody,
//            @Header(AmqpHeaders.MESSAGE_ID) String messageId,
//            Channel channel,
//            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
//            Message message
//    ) throws IOException {
//
//        // 打印接收到的消息基本信息
//        System.out.printf("收到消息！消息ID: %s, 路由键: %s%n",
//                messageId, message.getMessageProperties().getReceivedRoutingKey());
//
//        // 解析JSON消息体为Student对象
//        OrderMain orderMain = JSON.parseObject(messageBody, OrderMain.class);
//        System.out.println("解析后的订单ID:"+
//                orderMain.getId());
//
//
//
//        String token = null;
//        try {
//            // 1. 从消息headers中获取token
//            if (message.getMessageProperties().getHeaders().containsKey("token")) {
//                token = message.getMessageProperties().getHeader("token").toString();
//            }
//
//            // 2. 设置token到Feign拦截器的ThreadLocal中
//            FeignAuthRequestInterceptor.setMqToken(token);
//
//            // 3. 核心业务逻辑处理
//            orderAddingService.handleOrderAdd(orderMain);
//            channel.basicAck(deliveryTag, false);
//
//        } catch (Exception e) {
//            log.error("消费失败", e);
//            // 拒绝消息并重新入队（或根据业务死信队列）
//            channel.basicNack(deliveryTag, false, false);
//        }
//        finally {
//            // 4. 必须清除ThreadLocal中的token，防止内存泄漏
//            FeignAuthRequestInterceptor.clearMqToken();
//            RootContext.unbind();
//        }
//
//        System.out.printf("消息确认成功！消息ID: %s%n", messageId);
//
//
//    }
//
//
//
//}
