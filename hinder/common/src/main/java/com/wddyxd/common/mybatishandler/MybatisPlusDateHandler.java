package com.wddyxd.common.mybatishandler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: items-assigner
 * @description: 在实体类和数据库进行insert和update语句交互时自动填充 时间字段
 * @author: wddyxd
 * @create: 2025-10-20 20:04
 **/


@Component
public class MybatisPlusDateHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //属性名称，不是字段名称
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

}
