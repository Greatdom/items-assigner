package com.wddyxd.orderservice.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2026-01-27 15:42
 **/

@Configuration
public class RabbitOrderCancelTTLConfig {

    @Bean
    public DirectExchange ttlDirectExchange() {
        return new DirectExchange("ttl.direct");
    }

    @Bean
    public Queue ttlQueue() {
        return QueueBuilder
                .durable("ttl.order.queue")
                .ttl(900000)
                .deadLetterExchange("dl.direct")
                .deadLetterRoutingKey("dl.order")
                .build();
    }

    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with("ttl.order");
    }

    @Bean
    public DirectExchange dlDirectExchange() {
        return new DirectExchange("dl.direct", true, false);
    }

    @Bean
    public Queue dlOrderQueue() {
        return QueueBuilder.durable("dl.order.queue").build();
    }

    @Bean
    public Binding dlOrderBinding() {
        return BindingBuilder.bind(dlOrderQueue()).to(dlDirectExchange()).with("dl.order");
    }

//    public void sendOrderDelayMessage(String orderId) {
//        String message = orderId;
//        rabbitTemplate.convertAndSend(
//                "ttl.direct",
//                "ttl.order",
//                message
//        );
//    }
//
//    @RabbitListener(queues = "dl.order.queue")
//    public void handleTimeoutOrder(String orderId) {
//
//    }

}