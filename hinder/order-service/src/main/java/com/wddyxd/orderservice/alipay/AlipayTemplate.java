package com.wddyxd.orderservice.alipay;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
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

    public String pay(FinancialFlow financialFlow) throws
            AlipayApiException {

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
        return null;


    }

}
