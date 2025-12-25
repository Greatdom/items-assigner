package com.wddyxd.feign.pojo.productservice;


import java.math.BigDecimal;

/**
 * @program: items-assigner
 * @description: 查看某商品的规格接口的响应体
 * @author: wddyxd
 * @create: 2025-12-02 13:21
 **/

public class ProductSkuVO {
    private Long id;
    private Long productId;
    private String specs;
    private BigDecimal price;
    private Integer stock;
    private Integer sales;
    private String logo;

    @Override
    public String toString() {
        return "ProductSkuVO{" +
                "id=" + id +
                ", productId=" + productId +
                ", specs='" + specs + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", sales=" + sales +
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

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
