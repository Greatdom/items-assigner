package com.wddyxd.common.enumDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.wddyxd.common.exceptionhandler.CustomException;

import java.io.IOException;
import java.util.Arrays;
/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-16 14:34
 **/

public abstract class EnumByValueDeserializer<T extends Enum<T>> extends JsonDeserializer<T> {

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().trim();
        // 遍历枚举，匹配名称 或 自定义属性值
        return Arrays.stream(getEnumValues())
                .filter(e -> match(e, value))
                .findFirst()
                .orElseThrow(() -> new CustomException(400,"参数错误:无效的枚举值---- " + value));
    }


     //匹配规则：子类实现（匹配名称/自定义属性）

    protected abstract boolean match(T enumObj, String value);


    //获取枚举所有值：子类实现

    protected abstract T[] getEnumValues();
}