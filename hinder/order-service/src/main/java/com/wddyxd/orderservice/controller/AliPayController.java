package com.wddyxd.orderservice.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.easysdk.factory.Factory;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//    @Resource
//    AlipayTemplate alipayTemplate;
//
//    @GetMapping(value = "/pay", produces = "text/html")
//    @ResponseBody
//    public String pay(@RequestParam long id) throws AlipayApiException {
//        Order order = new Order();
//        order.setId(id);
//        order.setUserId(id+1);
//        order.setInterfaceInfoId(id+2);
//        order.setMoney(id+3.0);
//        order.setRefundId(id+4);
//        order.setPaymentMethod("支付宝");
//        return alipayTemplate.pay(order);
//    }
//
//    @GetMapping("/refund")
//    public void refund(@RequestParam long id) throws AlipayApiException {
//        Order order = new Order();
//        order.setId(id);
//        order.setUserId(id+1);
//        order.setInterfaceInfoId(id+2);
//        order.setMoney(id+3.0);
//        order.setRefundId(id+4);
//        order.setPaymentMethod("支付宝");
//        alipayTemplate.refund(order);
//    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            log.info("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("卖家在支付宝唯一id: " + params.get("seller_id"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
                // 更新订单状态
//                System.out.println("=========分账========");
//                Settle settle = new Settle();
//                settle.setId(params.get("trade_no"));
//                settle.setTransIn(params.get("seller_id"));
//                settle.setTransOut(params.get("seller_id"));
//                settle.setAmount(0.11);
//                settle.setTradeNo(params.get("trade_no"));
//                alipayTemplate.settlePay(settle);

            }else{
                System.out.println("验签失败");
            }
        }
        return "success";
    }
}