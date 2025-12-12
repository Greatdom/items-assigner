package com.wddyxd.productservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-10 12:00
 **/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    public Page<ProductProfileVO> getPageProductProfileVOManager(Page<ProductProfileVO> page, String search);

    Page<ProductProfileVO> getPageProductProfileVOFeed(
            IPage<ProductProfileVO> page,          // 分页对象（复用）
            @Param("categoryId") Long categoryId, // 分类ID（新条件）
            @Param("sortColumn") String sortColumn, // 排序字段（如 "sales"、"price"）
            @Param("sortOrder") String sortOrder   // 排序方式（"ASC"/"DESC"）
    );

}
