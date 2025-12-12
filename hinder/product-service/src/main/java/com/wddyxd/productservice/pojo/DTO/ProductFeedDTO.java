package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.pojo.SearchDTO;

/**
 * @program: items-assigner
 * @description: 商品推送接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 20:41
 **/

public class ProductFeedDTO extends SearchDTO {

    private Long categoryId;

    private String region;

    private String sort;

    @Override
    public String toString() {
        return "ProductFeedDTO{" +
                "categoryId=" + categoryId +
                ", region='" + region + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
