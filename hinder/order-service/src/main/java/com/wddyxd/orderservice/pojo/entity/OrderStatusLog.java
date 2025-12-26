package com.wddyxd.orderservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-24 00:13
 **/
@TableName("order_status_log")
public class OrderStatusLog extends BaseEntity implements Serializable {
    private Long orderId;
    private Long operatorId;//0-系统，其他为用户/商户/管理员ID
    private Date operateTime;//操作时间
    private Integer status;//0-待付款 1-待发货 2-待收货 3-已完成 4-已取消
    private String remark;//状态变更说明

    @Override
    public String toString() {
        return "OrderStatusLog{" +
                super.toString() +
                ", orderId=" + orderId +
                ", operatorId=" + operatorId +
                ", operateTime=" + operateTime +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
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