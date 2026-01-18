package com.wddyxd.orderservice.stateMachine;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.orderservice.pojo.entity.FinancialFlow;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.service.Interface.IFinancialFlowService;
import com.wddyxd.orderservice.service.Interface.ICommonOrderStatusLogService;
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
    private IFinancialFlowService financialFlowService;

    @Autowired
    private StateMachinePersister<OrderStatus, OrderEvent, Long> persister;

    @Autowired
    private ICommonOrderStatusLogService commonOrderStatusLogService;

    private static final Logger log = LoggerFactory.getLogger(StateMachineTrigger.class);

    public void doAction(OrderMain orderMain, FinancialFlow financialFlow,OrderEvent event){
        StateMachine<OrderStatus, OrderEvent> stateMachine = stateMachineFactory.getStateMachine(orderMain.getId().toString());

        try {
            // 从数据库恢复状态机上下文
            persister.restore(stateMachine, orderMain.getId());
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
            log.info("订单 {} 状态从 {} 迁移到 {}", orderMain.getId(), currentState, stateMachine.getState().getId());
            executeBusiness(orderMain,financialFlow, event);
            // 保存新的状态机上下文到数据库
            //TODO 用异步事件和重试机制完成事务
            //TODO 不过支付和退款接口时要等支付回调且更新财务后再更新订单状态
            try {
                persister.persist(stateMachine, orderMain.getId());
            } catch (Exception e) {
                log.error("保存状态机上下文失败", e);
                throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
            }
        } else {
            log.error("订单 {} 当前状态 {} 不允许执行事件 {}", orderMain.getId(), currentState, event);
        }
    }


    public void doAction(Long orderId, OrderEvent event) {
        OrderMain orderMain = new OrderMain();
        orderMain.setId(orderId);
        doAction(orderMain, null, event);
    }

    private void executeBusiness(OrderMain orderMain, FinancialFlow financialFlow, OrderEvent event) {
        switch (event) {
            //TODO PAY时调用回调方法
            case PAY -> financialFlowService.OrderPaid(orderMain,financialFlow);
            case SHIP -> commonOrderStatusLogService.ship(orderMain.getId());
            case RECEIVE -> commonOrderStatusLogService.receive(orderMain.getId());
            case CANCEL -> commonOrderStatusLogService.cancel(orderMain.getId());
            //TODO 退款时调用回调方法
            case ROLLBACK -> financialFlowService.OrderRefunded(orderMain.getId());
        }
    }

}
