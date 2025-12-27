package com.wddyxd.orderservice.stateMachine.Enum;

import com.wddyxd.common.exceptionhandler.CustomException;

public enum OrderStatus {
    PENDING_PAYMENT(0, "待付款"),
    PENDING_SHIPMENT(1, "待发货"),
    PENDING_RECEIPT(2, "待收货"),
    COMPLETED(3, "已完成"),
    CANCELLED(4, "已取消");

    private final int code;
    private final String desc;

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        //TODO 自定义异常
        throw new CustomException(400,"Invalid OrderStatus code: " + code);
    }
}