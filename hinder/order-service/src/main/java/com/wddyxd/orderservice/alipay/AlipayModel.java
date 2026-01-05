package com.wddyxd.orderservice.alipay;


import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wddyxd.orderservice.controller.AliPayController;
import com.wddyxd.orderservice.pojo.entity.FinancialFlow;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.service.Interface.IFinancialFlowService;
import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.StateMachineTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: 由于支付宝支付回调需要服务器连接外部网络,而网络资源宝贵,故建造模型来实现沙箱支付和假支付的切换
 * @author: wddyxd
 * @create: 2026-01-05 16:48
 **/
@Component
public class AlipayModel {

    private static final Logger log = LoggerFactory.getLogger(AlipayModel.class);


    @Autowired
    private AlipayServer alipayServer;

    @Autowired
    private StateMachineTrigger stateMachineTrigger;

    @Autowired
    private IFinancialFlowService financialFlowService;

    public String pay(FinancialFlow financialFlow)throws
            AlipayApiException {
        return modelPay(financialFlow);
    }

    public void refund(FinancialFlow financialFlow)throws
            AlipayApiException{
        modelRefund(financialFlow);
    }




    private String realPay(FinancialFlow financialFlow)throws
            AlipayApiException{
        return alipayServer.pay(financialFlow);
    }
    private void realRefund(FinancialFlow financialFlow)throws
            AlipayApiException{
        alipayServer.refund(financialFlow);
    }
    private String modelPay(FinancialFlow financialFlow){
        Long orderId = financialFlow.getOrderId();
        try {
            log.info("验签成功");
            FinancialFlow returnFinancialFlow = new FinancialFlow();
            financialFlow.setTransInId(IdWorker.getId()-1);
            financialFlow.setTransOutId(IdWorker.getId()+1);
            financialFlow.setTradeNo(IdWorker.getId()+"123");
            financialFlow.setPayMethod(2);
            OrderMain orderMain = new OrderMain();
            orderMain.setId(orderId);


            //TODO 异步操作
            //TODO 给平台分账

            //确定最终财务
            //更新订单和订单日志
            stateMachineTrigger.doAction(orderMain, financialFlow, OrderEvent.PAY);
            return "success";
        } catch (Exception e) {
            log.error("验签失败");
            financialFlowService.OrderPayingFail(orderId);
        }
        return "success";
    }
    private void modelRefund(FinancialFlow financialFlow){
        try {
            log.info("开始退款");
            stateMachineTrigger.doAction(financialFlow.getOrderId(), OrderEvent.ROLLBACK);
        } catch (Exception e) {
            log.info("退款失败");
            financialFlowService.OrderRefundingFail(financialFlow.getOrderId());
        }

    }

}
