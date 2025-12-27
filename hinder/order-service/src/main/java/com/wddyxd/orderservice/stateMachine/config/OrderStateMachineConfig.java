package com.wddyxd.orderservice.stateMachine.config;


import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.Enum.OrderStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import java.util.EnumSet;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-27 20:41
 **/


@Configuration
@EnableStateMachineFactory
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.PENDING_PAYMENT) // 初始状态为待付款
                .states(EnumSet.allOf(OrderStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvent> transitions) throws Exception {
        transitions
                // 待付款 -> 待发货 (支付)
                .withExternal()
                .source(OrderStatus.PENDING_PAYMENT)
                .target(OrderStatus.PENDING_SHIPMENT)
                .event(OrderEvent.PAY)
                .and()
                // 待发货 -> 待收货 (发货)
                .withExternal()
                .source(OrderStatus.PENDING_SHIPMENT)
                .target(OrderStatus.PENDING_RECEIPT)
                .event(OrderEvent.SHIP)
                .and()
                // 待收货 -> 已完成 (收货)
                .withExternal()
                .source(OrderStatus.PENDING_RECEIPT)
                .target(OrderStatus.COMPLETED)
                .event(OrderEvent.RECEIVE)
                .and()
                // 待付款 -> 已取消 (取消)
                .withExternal()
                .source(OrderStatus.PENDING_PAYMENT)
                .target(OrderStatus.CANCELLED)
                .event(OrderEvent.CANCEL)
                .and()
                // 其他状态 -> 已取消 (退货)
                .withExternal()
                .source(OrderStatus.PENDING_SHIPMENT)
                .target(OrderStatus.CANCELLED)
                .event(OrderEvent.ROLLBACK)
                .and()
                .withExternal()
                .source(OrderStatus.PENDING_RECEIPT)
                .target(OrderStatus.CANCELLED)
                .event(OrderEvent.ROLLBACK)
                .and()
                .withExternal()
                .source(OrderStatus.COMPLETED)
                .target(OrderStatus.CANCELLED)
                .event(OrderEvent.ROLLBACK);
    }
}