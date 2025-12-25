package com.wddyxd.productservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.productservice.pojo.DTO.ProductSkuDTO;
import com.wddyxd.productservice.pojo.VO.ProductSkuVO;
import com.wddyxd.productservice.pojo.entity.ProductSku;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:42
 **/

public interface IProductSkuService extends IService<ProductSku> {

    public List<ProductSkuVO> List(Long id);

    public void add(ProductSkuDTO productSkuDTO);

    public void updateCommon(ProductSkuDTO productSkuDTO);

    public void updateStock(ProductSkuDTO productSkuDTO);

    public void updateDefault(ProductSkuDTO productSkuDTO);

    public void updateConsume(Long skuId, Integer quantity);

    public void delete(Long id);

    void updateLogo(ProductSkuDTO productSkuDTO);
}
