package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * @program: items-assigner
 * @description: 添加商品规格接口的请求体
 * @author: wddyxd
 * @create: 2025-12-02 13:29
 **/

public class ProductSkuDTO {
    @Null(message = "新增商品SKU时ID必须为空", groups = AddGroup.class)
    @NotNull(message = "更新商品SKU时ID不能为空", groups = UpdateGroup.class)
    @Min(value = 1, message = "SKU ID必须大于0", groups = UpdateGroup.class)
    private Long id;
    @NotNull(message = "商品ID不能为空", groups = {AddGroup.class,UpdateGroup.class})
    @Min(value = 1, message = "商品ID必须大于0", groups = {AddGroup.class,UpdateGroup.class})
    private Long productId;
    @NotBlank(message = "SKU规格不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String specs;
    @NotNull(message = "SKU价格不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @DecimalMin(value = "0.01", message = "SKU价格必须大于0", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal price;
    @NotNull(message = "SKU库存不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 1, message = "SKU库存必须大于0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer stock;
    @NotNull(message = "是否默认SKU不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Boolean isDefault;
    @NotNull(message = "SKU封面图不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String logo;

    @Override
    public String toString() {
        return "ProductSkuDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", specs='" + specs + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", isDefault=" + isDefault +
                ", logo='" + logo + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
