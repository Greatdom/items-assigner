package com.wddyxd.orderservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:36
 **/

public class OrderDTO {
    @NotNull(message = "订单ID不能为空",groups = {UpdateGroup.class})
    @Min(value = 1,message = "订单ID必须大于0",groups = {UpdateGroup.class})
    private Long id;
    @NotNull(message = "订单状态不能为空",groups = {UpdateGroup.class})
    @Min(value = 0,message = "订单状态必须大于0",groups = {UpdateGroup.class})
    @Max(value = 5,message = "订单状态必须小于6",groups = {UpdateGroup.class})
    private Integer status;
    @NotNull(message = "支付方式不能为空",groups = {UpdateGroup.class})
    @Min(value = 0,message = "支付方式必须大于0",groups = {UpdateGroup.class})
    @Max(value = 2,message = "支付方式必须小于3",groups = {UpdateGroup.class})
    private Integer payMethod;
    @NotNull(message = "商品ID不能为空",groups = {AddGroup.class})
    @Min(value = 1,message = "商品ID必须大于0",groups = {AddGroup.class})
    private Long productId;
    @NotNull(message = "SKU ID不能为空",groups = {AddGroup.class})
    @Min(value = 1,message = "SKU ID必须大于0",groups = {AddGroup.class})
    private Long skuId;
    @NotNull(message = "购买数量不能为空",groups = {AddGroup.class})
    @Min(value = 1,message = "购买数量必须大于0",groups = {AddGroup.class})
    private Integer quantity;
    @NotNull(message = "用户优惠券ID数组不能为null", groups = {AddGroup.class})
    private Long[] couponIds;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", status=" + status +
                ", payMethod=" + payMethod +
                ", productId=" + productId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", couponIds=" + Arrays.toString(couponIds) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long[] getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(Long[] couponIds) {
        this.couponIds = couponIds;
    }
}
