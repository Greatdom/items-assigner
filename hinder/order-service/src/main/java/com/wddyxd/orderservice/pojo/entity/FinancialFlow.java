package com.wddyxd.orderservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-24 00:36
 **/
@TableName("financial_flow")
public class FinancialFlow extends BaseEntity implements Serializable {
    private Long buyerId;//出账者id
    private Long merchantId;//入账者id
    private Long refundId;//退款号
    private String tradeNo;//回调单号
    private Integer tradeType;//0-充值 1-提现 2-订单支付 3-退款 4-平台佣金
    private BigDecimal money;//金额
    private Integer status;//0-处理中 1-成功 2-失败
    private String remark;//交易备注
    private Integer payMethod;//支付方式 0-未知 1-微信支付 2-支付宝支付

    @Override
    public String toString() {
        return "FinancialFlow{" +
                super.toString() +
                "buyerId=" + buyerId +
                ", merchantId=" + merchantId +
                ", refundId=" + refundId +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeType=" + tradeType +
                ", money=" + money +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", payMethod=" + payMethod +
                '}';
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }
}
