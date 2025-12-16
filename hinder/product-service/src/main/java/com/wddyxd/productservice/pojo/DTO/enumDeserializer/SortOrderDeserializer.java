package com.wddyxd.productservice.pojo.DTO.enumDeserializer;


import com.wddyxd.common.enumDeserializer.EnumByValueDeserializer;
import com.wddyxd.productservice.pojo.DTO.ProductFeedDTO;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-16 14:39
 **/

// SortOrder 反序列化器（匹配 order 属性 或 枚举名称）
public class SortOrderDeserializer extends EnumByValueDeserializer<ProductFeedDTO.SortOrder> {
    @Override
    protected boolean match(ProductFeedDTO.SortOrder enumObj, String value) {
        return enumObj.getOrder().equals(value) || enumObj.name().equals(value);
    }

    @Override
    protected ProductFeedDTO.SortOrder[] getEnumValues() {
        return ProductFeedDTO.SortOrder.values();
    }
}