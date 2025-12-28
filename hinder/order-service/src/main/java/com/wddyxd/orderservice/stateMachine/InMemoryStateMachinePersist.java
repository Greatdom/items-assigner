package com.wddyxd.orderservice.stateMachine;


import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.Enum.OrderStatus;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
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

    private final Map<Long, StateMachineContext<OrderStatus, OrderEvent>> contexts = new HashMap<>();

    @Override
    public void write(StateMachineContext<OrderStatus, OrderEvent> context, Long orderId) {
        contexts.put(orderId, context);
    }

    @Override
    public StateMachineContext<OrderStatus, OrderEvent> read(Long orderId) {
        return contexts.get(orderId);
    }
}