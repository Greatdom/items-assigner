package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;

import java.util.List;

/**
 * @program: items-assigner
 * @description: 添加商品接口的请求体
 * @author: wddyxd
 * @create: 2025-12-02 12:41
 **/

public class ProductAddDTO {

    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private List<ProductSkuDTO> productSkuDTOS;

    public static void validate(ProductAddDTO productAddDTO){
        if(productAddDTO==null||productAddDTO.getCategoryId() == null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productAddDTO.getProductSkuDTOS()==null|| productAddDTO.getProductSkuDTOS().isEmpty())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productAddDTO.getName()==null||productAddDTO.getName().isEmpty())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productAddDTO.getDescription()==null||productAddDTO.getDescription().isEmpty())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(productAddDTO.getDescription().length()>60)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        for(ProductSkuDTO productSkuDTO : productAddDTO.getProductSkuDTOS())
            ProductSkuDTO.addValidations(productSkuDTO);
    }

    @Override
    public String toString() {
        return "ProductAddDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                ", productSkuDTOS=" + productSkuDTOS +
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

    public List<ProductSkuDTO> getProductSkuDTOS() {
        return productSkuDTOS;
    }

    public void setProductSkuDTOS(List<ProductSkuDTO> productSkuDTOS) {
        this.productSkuDTOS = productSkuDTOS;
    }
}
