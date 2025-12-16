package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * @program: items-assigner
 * @description: 添加商品接口的请求体
 * @author: wddyxd
 * @create: 2025-12-02 12:41
 **/

public class ProductAddDTO {

    private Long id;
    @NotBlank(message = "商品名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    @NotNull(message = "商品分类ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 1, message = "商品分类ID必须大于0", groups = {AddGroup.class, UpdateGroup.class})
    private Long categoryId;
    @NotBlank(message = "商品描述不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String description;
    @NotNull(message = "SKU列表不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, message = "SKU列表至少包含一个SKU", groups = {AddGroup.class, UpdateGroup.class})
    @Valid
    private List<ProductSkuDTO> productSkuDTOS;



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
