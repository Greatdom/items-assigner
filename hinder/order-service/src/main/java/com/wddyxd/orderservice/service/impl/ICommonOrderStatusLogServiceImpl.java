package com.wddyxd.orderservice.service.impl;


import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.orderservice.alipay.AlipayModel;
import com.wddyxd.orderservice.mapper.OrderStatusLogMapper;
import com.wddyxd.orderservice.pojo.entity.FinancialFlow;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.pojo.entity.OrderStatusLog;
import com.wddyxd.orderservice.service.Interface.IFinancialFlowService;
import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import com.wddyxd.orderservice.service.Interface.ICommonOrderStatusLogService;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:30
 **/
@Service
public class ICommonOrderStatusLogServiceImpl extends ServiceImpl<OrderStatusLogMapper, OrderStatusLog> implements ICommonOrderStatusLogService {

    private static final Logger log = LoggerFactory.getLogger(ICommonOrderStatusLogServiceImpl.class);

    @Override
    public OrderStatusLog list(Long id) {
        return null;
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


}
