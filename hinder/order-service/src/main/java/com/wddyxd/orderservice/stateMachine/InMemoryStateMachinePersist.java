package com.wddyxd.orderservice.stateMachine;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.orderservice.mapper.OrderMainMapper;
import com.wddyxd.orderservice.mapper.OrderStatusLogMapper;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;
import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.Enum.OrderStatus;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-28 12:06
 **/

@Component
public class InMemoryStateMachinePersist implements StateMachinePersist<OrderStatus, OrderEvent, Long> {

    @Autowired
    private OrderMainMapper orderMainMapper;

    @Autowired
    private OrderStatusLogMapper orderStatusLogMapper;

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    private static final Logger log = LoggerFactory.getLogger(InMemoryStateMachinePersist.class);


    @Override
    @Transactional
    public void write(StateMachineContext<OrderStatus, OrderEvent> context, Long orderId) {
        if(orderId == null||orderId <= 0||context.getState() == null){
            log.error("状态机读取状态发生错误");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        OrderMain orderMain = orderMainMapper.selectById(orderId);
        if(orderMain==null||orderMain.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        orderMain.setStatus(context.getState().getCode());
        orderMainMapper.updateById(orderMain);
        OrderStatusLog orderStatusLog = new OrderStatusLog();
        orderStatusLog.setOperatorId(getCurrentUserInfoService.getCurrentUserId());
        orderStatusLog.setOrderId(orderId);
        orderStatusLog.setStatus(context.getState().getCode());
        orderStatusLog.setRemark(context.getState().getDesc());
        orderStatusLog.setOperateTime(new Date());
        orderStatusLogMapper.insert(orderStatusLog);
    }

    @Override
    public StateMachineContext<OrderStatus, OrderEvent> read(Long orderId) {
        if(orderId == null||orderId <= 0){
            log.error("状态机写入状态发生错误");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        OrderMain orderMain = orderMainMapper.selectById(orderId);
        if(orderMain==null||orderMain.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        OrderStatus orderStatus = OrderStatus.fromCode(orderMain.getStatus());
        // 构造一个状态机上下文
        return new DefaultStateMachineContext<>(orderStatus, null, null, null);
    }
}