package com.wddyxd.orderservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * @program: items-assigner
 * @description: 添加财务的请求体
 * @author: wddyxd
 * @create: 2025-12-03 12:54
 **/

public class FinancialFlowDTO {
    @NotNull(message = "交易类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "交易类型必须大于0", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 4, message = "交易类型必须小于5", groups = {AddGroup.class, UpdateGroup.class})
    private Integer tradeType;
    @NotNull(message = "金额不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @DecimalMin(value = "0.01", message = "数额必须大于0", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal amount;

    @Override
    public String toString() {
        return "FinancialFlowDTO{" +
                "tradeType=" + tradeType +
                ", amount=" + amount +
                '}';
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
}
