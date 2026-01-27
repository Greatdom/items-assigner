package com.wddyxd.common.config;


import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2026-01-27 15:32
 **/

@Configuration
public class RabbitMQPublisherConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 从Spring容器中获取RabbitTemplate实例
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        // 设置ReturnsCallback回调
        rabbitTemplate.setReturnsCallback(returnedMessage -> {

            //判断是否是延迟消息
            if(isDelayedMessage(returnedMessage)){
                System.out.println("这是延迟队列消息");
                return;
            }

            // 消息退回时的处理逻辑
            System.err.println("===== 消息退回回调触发 =====");
            System.err.println("退回的消息内容: " + new String(returnedMessage.getMessage().getBody()));
            System.err.println("回复码: " + returnedMessage.getReplyCode());
            System.err.println("回复文本: " + returnedMessage.getReplyText());
            System.err.println("交换机: " + returnedMessage.getExchange());
            System.err.println("路由键: " + returnedMessage.getRoutingKey());

            // 可以根据业务需求添加更多处理逻辑，比如日志记录、告警、消息重发等
        });

        //必须开启消息退回机制
        rabbitTemplate.setMandatory(true);
    }

    public boolean isDelayedMessage(ReturnedMessage returnedMessage) {
        // 获取消息属性
        MessageProperties properties = returnedMessage.getMessage().getMessageProperties();
        if (properties == null || properties.getHeaders() == null) {
            return false;
        }
        Object delayHeader = properties.getReceivedDelay();
        return delayHeader != null && delayHeader instanceof Number && ((Number) delayHeader).longValue() > 0;
    }

}
