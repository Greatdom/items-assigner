package com.wddyxd.productservice.service.Interface;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.productservice.pojo.DTO.ProductAddDTO;
import com.wddyxd.productservice.pojo.DTO.ProductBasicUpdateDTO;
import com.wddyxd.productservice.pojo.DTO.ProductFeedDTO;
import com.wddyxd.productservice.pojo.DTO.ProductListDTO;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.entity.Product;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-10 12:01
 **/

public interface IProductService extends IService<Product> {

    public Page<ProductProfileVO> List(ProductListDTO productListDTO);

    public ProductProfileVO feed(ProductFeedDTO productFeedDTO);

    public ProductProfileVO visit(Long id);

    public ProductProfileVO detail(Long id);

    public void add(ProductAddDTO productAddDTO);

    public void update(ProductBasicUpdateDTO productBasicUpdateDTO);

    public void status(Long id);

    public void delete(Long id);

}
