package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;

import java.math.BigDecimal;

/**
 * @program: items-assigner
 * @description: 添加商品规格接口的请求体
 * @author: wddyxd
 * @create: 2025-12-02 13:29
 **/

public class ProductSkuDTO {

    private Long id;
    private Long productId;
    private String specs;
    private BigDecimal price;
    private Integer stock;
    private Boolean isDefault;
    private String logo;

    public static void addValidations(ProductSkuDTO productSkuDTO){
        if(productSkuDTO==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productSkuDTO.getSpecs()==null||productSkuDTO.getSpecs().isEmpty())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productSkuDTO.getPrice()==null||productSkuDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productSkuDTO.getStock()==null||productSkuDTO.getStock() < 0)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productSkuDTO.getIsDefault()==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    public static void updateValidations(ProductSkuDTO productSkuDTO){
        if(productSkuDTO==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productSkuDTO.getId()==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productSkuDTO.getSpecs()==null||productSkuDTO.getSpecs().isEmpty())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

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
