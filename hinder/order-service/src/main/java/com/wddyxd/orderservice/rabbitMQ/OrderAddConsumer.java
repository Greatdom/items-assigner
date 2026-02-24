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

import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.feign.interceptor.FeignAuthRequestInterceptor;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;

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
            }
        }
        String token = null;
        try {
            // 从消息headers中获取token
            if (headers.containsKey("token")) {
                token = headers.get("token").toString();
            }
            // 设置token到Feign拦截器的ThreadLocal中
            FeignAuthRequestInterceptor.setMqToken(token);
            // 核心业务逻辑处理
            OrderMain orderMain = JSON.parseObject(messageBody, OrderMain.class);
            log.info("解析后的订单ID: {}", orderMain.getId());
            orderAddingService.handleOrderAdd(orderMain);
            // 消费成功，手动确认
            channel.basicAck(deliveryTag, false);
            log.info("消息确认成功！消息ID: {}", messageId);
        } catch (Exception e) {
            log.error("消费失败，消息ID: {}, 重试次数: {}", messageId, retryCount, e);

            try {
                // 判断是否需要重试
                if (retryCount < MAX_RETRY_COUNT &&
                        !(e instanceof CustomException && Objects.equals(((CustomException) e).getCode(), ResultCodeEnum.STOCK_NOT_ENOUGH_ERROR.getCode()))) {
                    //重新投递到原队列末尾

                    int newRetryCount = retryCount + 1;
                    log.info("准备重试消息，消息ID: {}, 新重试次数: {}", messageId, newRetryCount);
                    MessageProperties newMsgProps = message.getMessageProperties();
                    newMsgProps.getHeaders().put("x-retry-count", newRetryCount);
                    newMsgProps.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    rabbitTemplate.convertAndSend(CommonConstant.ORDER_ADD_QUEUE,message);
                    log.info("消息重试投递成功，消息ID: {}, 重试次数: {}", messageId, newRetryCount);
                    // 确认原消息已处理
                    channel.basicNack(deliveryTag, false, false);
                } else {
                    // 重试次数达到上限，路由到错误交换机
                    log.warn("消息重试次数达到上限，路由到错误交换机，消息ID: {}", messageId);
                    // 构建错误消息属性，记录失败原因
                    MessageProperties errorMsgProps = message.getMessageProperties();
                    errorMsgProps.getHeaders().put("x-fail-reason", e.getMessage());
                    errorMsgProps.getHeaders().put("x-retry-max", MAX_RETRY_COUNT);

                    // 发送到错误交换机
                    rabbitTemplate.convertAndSend("error.direct", "error", messageBody, msg -> {
                        MessageProperties props = msg.getMessageProperties();
                        props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        String errorMsg = e.getMessage() != null ? e.getMessage() : "未知异常";
                        props.setHeader("x-fail-reason", errorMsg.length() > 2000 ? errorMsg.substring(0, 2000) : errorMsg);
                        props.setHeader("x-exception-type", e.getClass().getName());
                        props.setHeader("x-retry-max", MAX_RETRY_COUNT);
                        props.setHeader("x-origin-message-id", messageId);
                        try {
                            OrderMain orderMain = JSON.parseObject(messageBody, OrderMain.class);
                            props.setHeader("x-order-id", orderMain.getId());
                        } catch (Exception parseEx) {
                            props.setHeader("x-order-id", "解析失败：" + parseEx.getMessage());
                        }
                        props.setHeader("x-fail-timestamp", System.currentTimeMillis());
                        return msg;
                    });

                    // 确认原消息已处理
                    channel.basicNack(deliveryTag, false, false);
                }
            } catch (IOException ex) {
                log.error("处理失败消息时发生异常，消息ID: {}", messageId, ex);
                // 极端情况：投递错误交换机失败，触发死信（需提前配置队列死信）
                channel.basicNack(deliveryTag, false, false);
            }
        } finally {
            // 必须清除ThreadLocal中的token，防止内存泄漏
            FeignAuthRequestInterceptor.clearMqToken();
            RootContext.unbind();
        }
    }
}