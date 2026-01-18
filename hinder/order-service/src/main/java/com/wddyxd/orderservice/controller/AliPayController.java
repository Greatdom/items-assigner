package com.wddyxd.orderservice.controller;


import com.alipay.easysdk.factory.Factory;
import com.wddyxd.orderservice.pojo.entity.FinancialFlow;
import com.wddyxd.orderservice.pojo.entity.OrderMain;
import com.wddyxd.orderservice.service.Interface.IFinancialFlowService;
import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import com.wddyxd.orderservice.service.Interface.ICommonOrderStatusLogService;
import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.StateMachineTrigger;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-30 13:24
 **/

@RestController
@RequestMapping("/alipay")
public class AliPayController {

    private static final Logger log = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private IOrderMainService orderMainService;

    @Autowired
    private StateMachineTrigger stateMachineTrigger;

    @Autowired
    private IFinancialFlowService financialFlowService;


    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            log.info("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                 System.out.println(name + " = " + request.getParameter(name));
            }
            Long orderId = Long.parseLong(params.get("out_trade_no"));
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                log.info("验签成功");
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("卖家在支付宝唯一id: " + params.get("seller_id"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
                FinancialFlow financialFlow = new FinancialFlow();
                financialFlow.setTransInId(Long.valueOf(params.get("seller_id")));
                financialFlow.setTransOutId(Long.valueOf(params.get("buyer_id")));
                financialFlow.setTradeNo(params.get("trade_no"));
                financialFlow.setPayMethod(2);
                OrderMain orderMain = new OrderMain();
                orderMain.setId(orderId);


                //TODO 异步操作
                //TODO 给平台分账

                //确定最终财务
                //更新订单和订单日志
                stateMachineTrigger.doAction(orderMain, financialFlow, OrderEvent.PAY);


            }else{
                log.error("验签失败");
                financialFlowService.OrderPayingFail(orderId);
            }
        }
        return "success";
    }
}