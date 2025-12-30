package com.wddyxd.orderservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.orderservice.alipay.AlipayTemplate;
import com.wddyxd.orderservice.mapper.OrderStatusLogMapper;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;
import com.wddyxd.orderservice.service.Interface.IFinancialFlowService;
import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import com.wddyxd.orderservice.service.Interface.IOrderStatusLogService;
import com.wddyxd.orderservice.stateMachine.Enum.OrderStatus;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:30
 **/
@Service
public class IOrderStatusLogServiceImpl extends ServiceImpl<OrderStatusLogMapper, OrderStatusLog> implements IOrderStatusLogService {

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    @Autowired
    private IOrderMainService orderMainService;

    @Autowired
    private IFinancialFlowService financialFlowService;

    private static final Logger log = LoggerFactory.getLogger(IOrderStatusLogServiceImpl.class);

    @Override
    public void add(Long id, OrderStatus orderStatus) {
        if(orderStatus == null||id == null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        OrderStatusLog orderStatusLog = new OrderStatusLog();
        orderStatusLog.setOperatorId(getCurrentUserInfoService.getCurrentUserId());
        orderStatusLog.setOrderId(id);
        orderStatusLog.setStatus(orderStatus.getCode());
        orderStatusLog.setRemark(orderStatus.getDesc());
        orderStatusLog.setOperateTime(new Date());
        this.save(orderStatusLog);
    }

    @Override
    public OrderStatusLog list(Long id) {
        return null;
    }

    @Override
    public void pay(Long id) {
        log.info("执行支付业务，订单ID={}", id);
        //查询订单且判断订单合法和处于待支付状态
        OrderMain orderMain = orderMainService.getById(id);
        if(orderMain == null
                ||orderMain.getIsDeleted()
                ||orderMain.getStatus() != OrderStatus.PENDING_PAYMENT.getCode()){
            log.error("订单不存在或者已删除或者状态错误");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        //生成在支付财务
        financialFlowService.paying(orderMain);
        //调用订单支付宝/微信支付接口
    }

    @Override
    public void ship(Long id) {
        log.info("执行发货业务，订单ID={}", id);
    }

    @Override
    public void receive(Long id) {
        log.info("执行确认收货业务，订单ID={}", id);
    }

    @Override
    public void cancel(Long id) {
        log.info("执行取消订单业务，订单ID={}", id);
    }

    @Override
    public void rollback(Long id) {
        log.info("执行退货业务，订单ID={}", id);
        //查询订单且判断订单合法和处于待发货及之后的状态

        //生成在支付财务

        //调用订单支付宝/微信退款接口
    }
}
