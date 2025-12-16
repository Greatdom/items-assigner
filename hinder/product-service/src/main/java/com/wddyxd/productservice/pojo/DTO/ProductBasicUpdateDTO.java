package com.wddyxd.productservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @program: items-assigner
 * @description: 修改商品内容接口的请求体
 * @author: wddyxd
 * @create: 2025-12-02 12:48
 **/

public class ProductBasicUpdateDTO {
    @NotNull(message = "更新商品时ID不能为空", groups = UpdateGroup.class)
    @Min(value = 1, message = "ID必须大于0", groups = UpdateGroup.class)
    private Long id;
    @NotBlank(message = "商品名称不能为空", groups = { UpdateGroup.class})
    private String name;
    @NotNull(message = "分类ID不能为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "分类ID必须大于0", groups = {UpdateGroup.class})
    private Long categoryId;
    @NotBlank(message = "商品描述不能为空", groups = {UpdateGroup.class})
    private String description;
    @NotNull(message = "更新商品SKU时ID不能为空", groups = UpdateGroup.class)
    @Min(value = 1, message = "SKU ID必须大于0", groups = UpdateGroup.class)
    private Long productSkuId;

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
