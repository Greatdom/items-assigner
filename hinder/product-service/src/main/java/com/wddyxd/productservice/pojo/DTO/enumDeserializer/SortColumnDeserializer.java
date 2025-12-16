package com.wddyxd.productservice.pojo.DTO.enumDeserializer;


import com.wddyxd.common.enumDeserializer.EnumByValueDeserializer;
import com.wddyxd.productservice.pojo.DTO.ProductFeedDTO;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-16 14:39
 **/

// SortColumn 反序列化器（匹配 column 属性 或 枚举名称）
public class SortColumnDeserializer extends EnumByValueDeserializer<ProductFeedDTO.SortColumn> {
    @Override
    protected boolean match(ProductFeedDTO.SortColumn enumObj, String value) {
        return enumObj.getColumn().equals(value) || enumObj.name().equals(value);
    }

    @Override
    protected ProductFeedDTO.SortColumn[] getEnumValues() {
        return ProductFeedDTO.SortColumn.values();
    }
}