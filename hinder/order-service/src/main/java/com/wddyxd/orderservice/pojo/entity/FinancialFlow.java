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
    private Long userId;
    private Integer tradeType;//0-充值 1-提现 2-订单支付 3-退款 4-平台佣金
    private BigDecimal amount;//金额
    private Integer status;//0-处理中 1-成功 2-失败
    private String remark;//交易备注

    @Override
    public String toString() {
        return "FinancialFlow{" +
                super.toString() +
                ", userId=" + userId +
                ", tradeType=" + tradeType +
                ", amount=" + amount +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

}
