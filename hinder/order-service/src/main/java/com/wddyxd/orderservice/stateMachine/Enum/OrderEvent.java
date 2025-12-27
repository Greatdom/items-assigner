package com.wddyxd.orderservice.stateMachine.Enum;

public enum OrderEvent {
    PAY,        // 支付
    SHIP,       // 发货
    RECEIVE,    // 收货
    CANCEL,     // 取消
    ROLLBACK    // 退货
}