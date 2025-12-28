package com.wddyxd.orderservice.stateMachine;


import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import com.wddyxd.orderservice.service.Interface.IOrderStatusLogService;
import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.Enum.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-28 12:06
 **/

@Component
public class InMemoryStateMachinePersist implements StateMachinePersist<OrderStatus, OrderEvent, Long> {

    @Autowired
    private IOrderMainService orderMainService;

    @Autowired
    private IOrderStatusLogService orderStatusLogService;

    @Override
    public void write(StateMachineContext<OrderStatus, OrderEvent> context, Long orderId) {
        orderMainService.update(orderId, context.getState());
        orderStatusLogService.add(orderId, context.getState());
    }

    @Override
    public StateMachineContext<OrderStatus, OrderEvent> read(Long orderId) {
        OrderStatus orderStatus = orderMainService.getOrderStatus(orderId);
        if (orderStatus == null) {
            // 如果没有状态，返回null，状态机会走初始状态
            return null;
        }
        // 构造一个状态机上下文
        return new DefaultStateMachineContext<>(orderStatus, null, null, null);
    }
}