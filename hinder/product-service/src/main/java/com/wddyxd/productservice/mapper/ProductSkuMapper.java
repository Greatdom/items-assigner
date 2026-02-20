package com.wddyxd.productservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.productservice.pojo.VO.ProductSkuVO;
import com.wddyxd.productservice.pojo.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:41
 **/
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    List<ProductSkuVO> selectProductSkuVOByProductId(Long productId);

    int updateStock(
            @Param("skuId") Long skuId,
            @Param("oldVersion") Long oldVersion,
            @Param("quantity") Integer quantity
    );

}
