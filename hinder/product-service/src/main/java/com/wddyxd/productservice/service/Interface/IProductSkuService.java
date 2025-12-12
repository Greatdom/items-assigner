package com.wddyxd.productservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.productservice.pojo.entity.ProductSku;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:42
 **/

public interface IProductSkuService extends IService<ProductSku> {

    public void List(Long id);

    public void add(Long id);

    public void update(Long id);

    public void delete(Long id);

}
