package com.wddyxd.orderservice.alipay;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeOrderSettleRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeOrderSettleResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.orderservice.controller.OrderMainController;
import com.wddyxd.orderservice.pojo.entity.FinancialFlow;
import com.wddyxd.orderservice.service.Interface.IFinancialFlowService;
import com.wddyxd.orderservice.service.Interface.IOrderMainService;
import com.wddyxd.orderservice.service.Interface.IOrderStatusLogService;
import com.wddyxd.orderservice.stateMachine.Enum.OrderEvent;
import com.wddyxd.orderservice.stateMachine.Enum.OrderStatus;
import com.wddyxd.orderservice.stateMachine.StateMachineTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-30 09:50
 **/

@Component
public class AlipayTemplate {

    private static final Logger log = LoggerFactory.getLogger(AlipayTemplate.class);

    @Autowired
    private IFinancialFlowService financialFlowService;

    @Autowired
    private StateMachineTrigger stateMachineTrigger;

    @Bean
    public void initAlipaySdk() {
        Config config = new Config();
        config.appId = CommonConstant.ALIPAY_APP_ID;
        config.merchantPrivateKey = CommonConstant.ALIPAY_PRIVATE_KEY;
        config.alipayPublicKey = CommonConstant.ALIPAY_PUBLIC_KEY;
        config.gatewayHost = CommonConstant.ALIPAY_GATEWAY_URL;
        config.signType = CommonConstant.SIGN_TYPE;

        // 初始化SDK
        Factory.setOptions(config);
        log.info("初始化支付宝SDK成功");
    }

    // 支付请求
    public String pay(FinancialFlow financialFlow) throws
            AlipayApiException {

        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = getAlipayClient();

        //2、创建一个支付请求，并设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(CommonConstant.ALIPAY_RETURN_URL);
        alipayRequest.setNotifyUrl(CommonConstant.ALIPAY_NOTIFY_URL);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_trade_no", financialFlow.getOutTradeNo());
        jsonObject.put("total_amount", financialFlow.getMoney());
        jsonObject.put("subject", financialFlow.getRemark());
        jsonObject.put("body", financialFlow.getRemark());
        jsonObject.put("timeout_express", "5m");
        jsonObject.put("product_code", "FAST_INSTANT_TRADE_PAY");

        alipayRequest.setBizContent(jsonObject.toString());
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        log.info("支付宝的响应：{}", result);
        return result;


    }

    // 退款请求
    public void refund(FinancialFlow financialFlow) throws
            AlipayApiException {

        AlipayClient alipayClient = getAlipayClient();

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

        JSONObject jsonObject = new JSONObject();
        // 1. 确保订单号是字符串类型（避免数字格式问题）
        jsonObject.put("out_trade_no", String.valueOf(financialFlow.getOutTradeNo()));
        // 2. 退款金额保留2位小数（符合支付宝金额规范）
        jsonObject.put("refund_amount", String.format("%.2f", financialFlow.getMoney()));
        // 3. 退款请求号使用唯一值（建议用UUID，避免重复）
        String refundRequestNo = String.valueOf(financialFlow.getId()) + "_" + System.currentTimeMillis();
        jsonObject.put("out_request_no", refundRequestNo);

        request.setBizContent(jsonObject.toString());
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            log.info("退款成功：{}", response.getBody());

//            Long orderId = financialFlow.getOrderId();
//            OrderStatus orderStatus = OrderStatus.CANCELLED;
//
              //TODO 异步操作
//            //TODO 给平台分账退款
//
//            //TODO 确定最终财务
//            //TODO 更新订单和订单日志
            stateMachineTrigger.doAction(financialFlow.getOrderId(), OrderEvent.ROLLBACK);


        } else {
            log.info("退款失败：{}", response.getBody());
            financialFlowService.OrderRefundingFail(financialFlow.getOrderId());
        }
    }

    // 分账请求
    public void settlePay(FinancialFlow financialFlow) throws
            AlipayApiException {

        AlipayClient alipayClient = getAlipayClient();
        AlipayTradeOrderSettleRequest request = new AlipayTradeOrderSettleRequest();
        JSONObject jsonObject = new JSONObject();
        String refundRequestNo = String.valueOf(financialFlow.getId()) + "_" + System.currentTimeMillis();
        jsonObject.put("out_request_no", refundRequestNo);
        jsonObject.put("trade_no", financialFlow.getTradeNo());
        JSONArray royaltyParameters = new JSONArray();
        JSONObject royaltyParam = new JSONObject();
        royaltyParam.put("trans_out", financialFlow.getTransOutId()); // 出账方
        royaltyParam.put("trans_in", financialFlow.getTransInId());   // 入账方
        royaltyParam.put("amount", String.format("%.2f", financialFlow.getMoney())); // 金额保留2位小数
        royaltyParameters.add(royaltyParam);
        jsonObject.put("royalty_parameters", royaltyParameters); // 传入数组
        request.setBizContent(jsonObject.toString());


        request.setBizContent(jsonObject.toString());
        AlipayTradeOrderSettleResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("分账成功：" + response.getBody());
        } else {
            System.out.println("分账失败：" + response.getSubMsg());
        }


    }

    // 分账退款请求
//    public void settleRefund(Order order) throws
//            AlipayApiException {
//        AlipayClient alipayClient = getAlipayClient();
//        AlipayTradeOrderSettleRefundRequest request = new AlipayTradeOrderSettleRefundRequest();
//        AlipayTradeOrderSettleRefundRequest request = new AlipayTradeOrderSettleRefundRequest();
//        request.setBizContent("{"
//                + "\"out_request_no\":\"20240620001_SettleRefund001\","
//                + "\"trade_no\":\"20240620220014123456789012345678901234567890\","
//                + "\"refund_royalty_parameters\":[{"
//                + "\"trans_out\":\"2088102146225135\","
//                + "\"trans_in\":\"2088102146225136\","
//                + "\"amount\":\"0.01\""
//                + "}]"
//                + "}");
//        AlipayTradeOrderSettleRefundResponse response = alipayClient.execute(request);
//        if (response.isSuccess()) {
//            System.out.println("分账退款成功：" + response.getBody());
//        } else {
//            System.out.println("分账退款失败：" + response.getSubMsg());
//        }
//    }

    private AlipayClient getAlipayClient() {
        return new DefaultAlipayClient(CommonConstant.ALIPAY_GATEWAY_URL,
                CommonConstant.ALIPAY_APP_ID,
                CommonConstant.ALIPAY_PRIVATE_KEY,
                "json",
                CommonConstant.CHARSET,
                CommonConstant.ALIPAY_PUBLIC_KEY,
                CommonConstant.SIGN_TYPE);
    }

}
