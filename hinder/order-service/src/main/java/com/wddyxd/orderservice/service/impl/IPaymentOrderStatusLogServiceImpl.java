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
import com.wddyxd.orderservice.service.Interface.IPaymentOrderStatusLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2026-01-18 11:48
 **/
@Service
public class IPaymentOrderStatusLogServiceImpl extends ServiceImpl<OrderStatusLogMapper, OrderStatusLog> implements IPaymentOrderStatusLogService {

    private static final Logger log = LoggerFactory.getLogger(IPaymentOrderStatusLogServiceImpl.class);

    @Autowired
    private IOrderMainService orderMainService;

    @Autowired
    private IFinancialFlowService financialFlowService;

    @Autowired
    private AlipayModel alipayModel;

    @Override
    @Transactional
    public String pay(Long id) {
        log.info("执行支付业务，订单ID={}", id);
        //查询订单且判断订单合法和处于待支付状态
        OrderMain orderMain = orderMainService.getById(id);
//        if(orderMain == null
//                ||orderMain.getIsDeleted()
//                ||orderMain.getStatus() != OrderStatus.PENDING_PAYMENT.getCode()){
//            log.error("订单不存在或者已删除或者状态错误");
//            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
//        }
        //生成在支付财务
        FinancialFlow financialFlow = financialFlowService.OrderPaying(orderMain);
        //调用订单支付宝/微信支付接口
        try {
            return alipayModel.pay(financialFlow);
        } catch (AlipayApiException e) {
            log.error("试图发起支付宝支付时异常");
            throw new CustomException(ResultCodeEnum.UNKNOWN_ERROR);
        }
    }

    @Override
    @Transactional
    public void rollback(Long id) {
        log.info("执行退货业务，订单ID={}", id);
        //查询订单且判断订单合法和处于待发货及之后的状态
        OrderMain orderMain = orderMainService.getById(id);
//        if(orderMain == null
//                ||orderMain.getIsDeleted()){
//            log.error("订单不存在或者已删除或者状态错误");
//            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
//        }
//        if(orderMain.getStatus() != OrderStatus.PENDING_SHIPMENT.getCode()&&
//        orderMain.getStatus() != OrderStatus.PENDING_RECEIPT.getCode()&&
//        orderMain.getStatus() != OrderStatus.COMPLETED.getCode()
//        ){
//            log.error("订单不存在或者已删除或者状态错误");
//            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
//        }

        //生成在退款财务
        FinancialFlow dbFinancialFlow = financialFlowService.getBaseMapper().selectOne(
                new LambdaQueryWrapper<FinancialFlow>()
                        .eq(FinancialFlow::getOrderId, id)
        );
        if(dbFinancialFlow == null||dbFinancialFlow.getIsDeleted()){
            log.error("财务不存在或者已删除");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }

        FinancialFlow financialFlow = financialFlowService.OrderRefunding(dbFinancialFlow);

        //调用订单支付宝/微信退款接口
        try {
            alipayModel.refund(financialFlow);
        } catch (AlipayApiException e) {
            log.error("试图发起支付宝退款时异常");
            throw new CustomException(ResultCodeEnum.UNKNOWN_ERROR);
        }
    }

}
