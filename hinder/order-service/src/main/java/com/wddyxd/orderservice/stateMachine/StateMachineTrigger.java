package com.wddyxd.orderservice.stateMachine;


import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.Enum.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-28 12:05
 **/
@Component
public class StateMachineTrigger {

    @Autowired
    private StateMachineFactory<OrderStatus, OrderEvent> stateMachineFactory;


    @Autowired
    private StateMachinePersister<OrderStatus, OrderEvent, Long> persister;

    public void doAction(Long orderId, OrderEvent event) {
        StateMachine<OrderStatus, OrderEvent> stateMachine = stateMachineFactory.getStateMachine(orderId.toString());
        try {
            // 从数据库恢复状态机上下文
            persister.restore(stateMachine, orderId);

            // 启动状态机（恢复到数据库中的状态）
            stateMachine.startReactively().block();

            OrderStatus currentState = stateMachine.getState().getId();
            boolean success = stateMachine.sendEvent(event);

            if (success) {
                System.out.println("订单 " + orderId + " 状态从 " + currentState + " 迁移到 " + stateMachine.getState().getId());
                executeBusiness(orderId, event);
                // 保存新的状态机上下文到数据库
                persister.persist(stateMachine, orderId);
            } else {
                System.out.println("订单 " + orderId + " 当前状态 " + currentState + " 不允许执行事件 " + event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeBusiness(Long orderId, OrderEvent event) {
        switch (event) {
            case PAY -> System.out.println("执行支付业务，订单ID=" + orderId);
            case SHIP -> System.out.println("执行发货业务，订单ID=" + orderId);
            case RECEIVE -> System.out.println("执行确认收货业务，订单ID=" + orderId);
            case CANCEL -> System.out.println("执行取消订单业务，订单ID=" + orderId);
            case ROLLBACK -> System.out.println("执行退货业务，订单ID=" + orderId);
        }
    }

}
