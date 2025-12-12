package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;

/**
 * @program: items-assigner
 * @description: 修改商品内容接口的请求体
 * @author: wddyxd
 * @create: 2025-12-02 12:48
 **/

public class ProductBasicUpdateDTO {

    private Long id;

    private String name;

    private Long categoryId;

    private String description;

    private Long productSkuId;

    public static void validate(ProductBasicUpdateDTO productBasicUpdateDTO){
        if(productBasicUpdateDTO==null||productBasicUpdateDTO.getId()==null||productBasicUpdateDTO.getId()<=0)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productBasicUpdateDTO.getName()==null||productBasicUpdateDTO.getName().isEmpty())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productBasicUpdateDTO.getCategoryId()==null||productBasicUpdateDTO.getCategoryId()<=0)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productBasicUpdateDTO.getDescription()==null||productBasicUpdateDTO.getDescription().isEmpty())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productBasicUpdateDTO.getProductSkuId()==null||productBasicUpdateDTO.getProductSkuId()<=0)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public String toString() {
        return "ProductBasicUpdateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                ", productSkuId=" + productSkuId +
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }
}
