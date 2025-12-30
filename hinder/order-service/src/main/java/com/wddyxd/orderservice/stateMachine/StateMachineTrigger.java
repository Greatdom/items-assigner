package com.wddyxd.orderservice.stateMachine;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.orderservice.controller.OrderStatusLogController;
import com.wddyxd.orderservice.service.Interface.IOrderStatusLogService;
import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.Enum.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private IOrderStatusLogService orderStatusLogService;

    private static final Logger log = LoggerFactory.getLogger(StateMachineTrigger.class);


    public String doAction(Long orderId, OrderEvent event) {
        StateMachine<OrderStatus, OrderEvent> stateMachine = stateMachineFactory.getStateMachine(orderId.toString());

        try {
            // 从数据库恢复状态机上下文
            persister.restore(stateMachine, orderId);
        } catch (Exception e) {
            log.error("恢复状态机上下文失败", e);
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }

        // 启动状态机,恢复到数据库中的状态
        stateMachine.startReactively().block();

        OrderStatus currentState = stateMachine.getState().getId();
        boolean success = stateMachine.sendEvent(event);
        String result = null;
        if (success) {
            log.info("订单 {} 状态从 {} 迁移到 {}", orderId, currentState, stateMachine.getState().getId());
            result = executeBusiness(orderId, event);
            // 保存新的状态机上下文到数据库
            //TODO 用异步事件和重试机制完成事务
            //TODO 不过支付和退款接口时要等支付回调且更新财务后再更新订单状态
            try {
                persister.persist(stateMachine, orderId);
            } catch (Exception e) {
                log.error("保存状态机上下文失败", e);
                throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
            }
        } else {
            log.error("订单 {} 当前状态 {} 不允许执行事件 {}", orderId, currentState, event);
        }
        return result;

    }

    private String executeBusiness(Long orderId, OrderEvent event) {
        String result = null;
        switch (event) {
            case PAY -> result = orderStatusLogService.pay(orderId);
            case SHIP -> orderStatusLogService.ship(orderId);
            case RECEIVE -> orderStatusLogService.receive(orderId);
            case CANCEL -> orderStatusLogService.cancel(orderId);
            case ROLLBACK -> orderStatusLogService.rollback(orderId);
        }
        return result;
    }

}
