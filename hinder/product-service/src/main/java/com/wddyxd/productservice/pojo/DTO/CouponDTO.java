package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.Interface.AddGroup;
import com.wddyxd.common.Interface.UpdateGroup;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 优惠券请求体
 * @author: wddyxd
 * @create: 2025-12-02 14:40
 **/

public class CouponDTO {

    @Null(message = "新增优惠券时ID必须为空", groups = AddGroup.class)
    @NotNull(message = "更新优惠券时ID不能为空", groups = UpdateGroup.class)
    @Min(value = 1, message = "优惠券ID必须大于0", groups = UpdateGroup.class)
    private Long id;

    @NotBlank(message = "优惠券名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    @NotNull(message = "获取类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "获取类型不能小于0", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "获取类型不能大于1", groups = {AddGroup.class, UpdateGroup.class})
    private Integer getType;

    @NotNull(message = "目标类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "目标类型不能小于0", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 2, message = "目标类型不能大于2", groups = {AddGroup.class, UpdateGroup.class})
    private Integer targetType;

    @NotNull(message = "是否折扣标识不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "是否折扣标识不能小于0", groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 1, message = "是否折扣标识不能大于1", groups = {AddGroup.class, UpdateGroup.class})
    private Integer isDiscount;

    @AssertTrue(message = "目标类型为0时目标ID必须为空", groups = {AddGroup.class, UpdateGroup.class})
    public boolean isTargetIdValid() {
        // targetType为0时，targetId必须为空（默认值0）
        if (targetType != null && targetType == 0) {
            return targetId == null || targetId == 0;
        }
        // targetType不为0时，targetId必须大于0
        return targetId != null && targetId > 0;
    }
    private Integer targetId = 0;

    @NotNull(message = "使用门槛不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @DecimalMin(value = "0.01", inclusive = true, message = "使用门槛必须大于0", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal threshold;

    @AssertTrue(message = "获取类型为1时优惠券值必须在0.01~100之间，其他类型必须大于0", groups = {AddGroup.class, UpdateGroup.class})
    public boolean isValueValid() {
        if (value == null || getType == null) return false;
        if (getType == 1) {
            return value.compareTo(new BigDecimal("0.01")) >= 0
                    && value.compareTo(new BigDecimal("100")) <= 0;
        }
        return value.compareTo(BigDecimal.ZERO) > 0;
    }
    @NotNull(message = "优惠券值不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal value;

    @AssertTrue(message = "开始时间必须早于结束时间", groups = {AddGroup.class, UpdateGroup.class})
    public boolean isStartTimeBeforeEndTime() {
        if (startTime == null || endTime == null) return false;
        return startTime.before(endTime);
    }
    @NotNull(message = "开始时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Date startTime;

    @NotNull(message = "结束时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Date endTime;

    @NotNull(message = "库存不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 1, message = "库存必须大于0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer stock;

    @Override
    public String toString() {
        return "CouponDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", getType=" + getType +
                ", targetType=" + targetType +
                ", isDiscount=" + isDiscount +
                ", targetId=" + targetId +
                ", threshold=" + threshold +
                ", value=" + value +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", stock=" + stock +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGetType() {
        return getType;
    }

    public void setGetType(Integer getType) {
        this.getType = getType;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(Integer isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
