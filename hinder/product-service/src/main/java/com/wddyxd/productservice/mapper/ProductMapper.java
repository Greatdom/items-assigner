package com.wddyxd.productservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.productservice.pojo.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-10 12:00
 **/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
