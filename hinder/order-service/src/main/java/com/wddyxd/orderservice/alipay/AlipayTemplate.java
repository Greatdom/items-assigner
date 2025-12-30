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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//    public String pay(Order order) throws
//            AlipayApiException {
//
//        //1、根据支付宝的配置生成一个支付客户端
//        AlipayClient alipayClient = getAlipayClient();
//
//        //2、创建一个支付请求，并设置请求参数
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        alipayRequest.setReturnUrl(returnUrl);
//        alipayRequest.setNotifyUrl(notifyUrl);
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("out_trade_no", order.getId());
//        jsonObject.put("total_amount", order.getMoney());
//        jsonObject.put("subject", order.getInterfaceInfoId());
//        jsonObject.put("body", order.getPaymentMethod());
//        jsonObject.put("timeout_express", timeout);
//        jsonObject.put("product_code", "FAST_INSTANT_TRADE_PAY");
//
//        alipayRequest.setBizContent(jsonObject.toString());
//        String result = alipayClient.pageExecute(alipayRequest).getBody();
//        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
//        System.out.println("支付宝的响应：" + result);
//        return result;
//
//
//
//    }

//    public void refund(Order order) throws
//            AlipayApiException {
//
//        AlipayClient alipayClient = getAlipayClient();
//
//        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
//
//        JSONObject jsonObject = new JSONObject();
//        // 1. 确保订单号是字符串类型（避免数字格式问题）
//        jsonObject.put("out_trade_no", String.valueOf(order.getId()));
//        // 2. 退款金额保留2位小数（符合支付宝金额规范）
//        jsonObject.put("refund_amount", String.format("%.2f", order.getMoney()));
//        // 3. 退款请求号使用唯一值（建议用UUID，避免重复）
//        String refundRequestNo = String.valueOf(order.getRefundId()) + "_" + System.currentTimeMillis();
//        jsonObject.put("out_request_no", refundRequestNo);
//
//        request.setBizContent(jsonObject.toString());
//        AlipayTradeRefundResponse response = alipayClient.execute(request);
//        if (response.isSuccess()) {
//            System.out.println("退款成功：" + response.getBody());
//        } else {
//            System.out.println("退款失败：" + response.getSubMsg());
//        }
//    }

//    public void settlePay(Settle settle) throws
//            AlipayApiException {
//
//        AlipayClient alipayClient = getAlipayClient();
//        AlipayTradeOrderSettleRequest request = new AlipayTradeOrderSettleRequest();
//        JSONObject jsonObject = new JSONObject();
//        String refundRequestNo = String.valueOf(settle.getId()) + "_" + System.currentTimeMillis();
//        jsonObject.put("out_request_no", refundRequestNo);
//        jsonObject.put("trade_no", settle.getTradeNo());
//        JSONArray royaltyParameters = new JSONArray();
//        JSONObject royaltyParam = new JSONObject();
//        royaltyParam.put("trans_out", settle.getTransOut()); // 出账方
//        royaltyParam.put("trans_in", settle.getTransIn());   // 入账方
//        royaltyParam.put("amount", String.format("%.2f", settle.getAmount())); // 金额保留2位小数
//        royaltyParameters.add(royaltyParam);
//        jsonObject.put("royalty_parameters", royaltyParameters); // 传入数组
//        request.setBizContent(jsonObject.toString());
//
//
//        request.setBizContent(jsonObject.toString());
//        AlipayTradeOrderSettleResponse response = alipayClient.execute(request);
//        if (response.isSuccess()) {
//            System.out.println("分账成功：" + response.getBody());
//        } else {
//            System.out.println("分账失败：" + response.getSubMsg());
//        }
//
//
//    }

//    public void settleRefund(Order order) throws
//            AlipayApiException {
//        AlipayClient alipayClient = getAlipayClient();
//        AlipayTradeOrderSettleRefundRequest request = new AlipayTradeOrderSettleRefundRequest();

//        {
//            "out_request_no": "分账退款请求号",
//                "trade_no": "支付宝交易号",
//                "refund_royalty_parameters": [
//            {
//                "trans_out": "分账退款出账方",
//                    "trans_in": "分账退款入账方",
//                    "amount": "分账退款金额"
//            }
//  ]
//        }
//
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

//    private AlipayClient getAlipayClient() {
//        return new DefaultAlipayClient(gatewayUrl, appId, merchantPrivateKey,
//                "json", charset, alipayPublicKey, signType);
//    }

}
