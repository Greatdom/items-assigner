package com.wddyxd.productservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.common.constant.RedisKeyConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.productservice.controller.ProductSkuController;
import com.wddyxd.productservice.mapper.ProductMapper;
import com.wddyxd.productservice.mapper.ProductSkuMapper;
import com.wddyxd.productservice.pojo.DTO.ProductSkuDTO;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.VO.ProductSkuVO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import com.wddyxd.productservice.pojo.entity.Product;
import com.wddyxd.productservice.pojo.entity.ProductSku;
import com.wddyxd.productservice.service.Interface.IProductSkuService;
import com.wddyxd.productservice.service.Interface.IUserCouponService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:43
 **/
@Service
public class IProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements IProductSkuService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<ProductSkuVO> List(Long id) {

        List<ProductSkuVO> productSkuVOS = null;
        Object redisGetProductSkuVOS = redisTemplate.opsForValue().get(RedisKeyConstant.STORE_PRODUCT_SKU_LIST.key+id);
        if(redisGetProductSkuVOS!=null){
            productSkuVOS = (ArrayList<ProductSkuVO>) redisGetProductSkuVOS;
            if(productSkuVOS.getFirst().getId()==null){
                log.error("商品规格不存在");
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            }
        }
        productSkuVOS = baseMapper.selectProductSkuVOByProductId(id);
        redisTemplate.opsForValue().set(RedisKeyConstant.STORE_PRODUCT_SKU_LIST.key+id, Objects.requireNonNullElseGet(productSkuVOS, ArrayList<ProductSkuVO>::new),10, TimeUnit.MINUTES);
        if(productSkuVOS==null){
            log.error("商品规格不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        return productSkuVOS;
    }

    private static final Logger log = LoggerFactory.getLogger(IProductSkuServiceImpl.class);

    @Override
    @Transactional
    public void add(ProductSkuDTO productSkuDTO) {
        // 判断商品规格数量是否超出限制,但不需要加锁
        long count = baseMapper.selectCount(new LambdaQueryWrapper<ProductSku>()
                .eq(ProductSku::getProductId, productSkuDTO.getProductId())
                .eq(ProductSku::getIsDeleted, false)
        );
        if(count> CommonConstant.MAX_PRODUCT_SKU_NUM)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        // 添加商品规格
        ProductSku productSku = new ProductSku();
        BeanUtil.copyProperties(productSkuDTO, productSku);
        productSku.setId(IdWorker.getId());
        // 获取商品
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getId, productSkuDTO.getProductId()));
        if(product == null||product.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        int productStock = product.getStock();
        // 设置默认商品规格
        if(productSkuDTO.getIsDefault()){
            product.setProductSkuId(productSku.getId());
        }
        //计算库存
        product.setStock(product.getStock()+productSku.getStock());
        // 执行乐观锁更新
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
                .eq(Product::getId, product.getId())
                .eq(Product::getStock, productStock)
                .eq(Product::getIsDeleted, false);

        int updateCount = productMapper.update(product, updateWrapper);
        if (updateCount == 0)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);

        baseMapper.insert(productSku);

    }

    @Override
    public void updateCommon(ProductSkuDTO productSkuDTO) {
        //得到旧的商品规格
        ProductSku productSku = baseMapper.selectById(productSkuDTO.getId());
        if(productSku == null||productSku.getIsDeleted()) {
            log.error("商品规格不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        productSku.setProductId(productSkuDTO.getProductId());
        productSku.setSpecs(productSkuDTO.getSpecs());
        productSku.setPrice(productSkuDTO.getPrice());
        baseMapper.updateById(productSku);
    }

    @Override
    public void updateStock(ProductSkuDTO productSkuDTO) {
        ProductSku productSku = baseMapper.selectById(productSkuDTO.getId());
        if(productSku == null||productSku.getIsDeleted()) {
            log.error("商品规格不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        productSku.setStock(productSkuDTO.getStock());
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getId, productSkuDTO.getProductId()));
        if(product == null||product.getIsDeleted()) {
            log.error("商品不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        product.setStock(product.getStock()-productSkuDTO.getStock()+productSkuDTO.getStock());
        productMapper.updateById(product);
        baseMapper.updateById(productSku);
    }

    @Override
    public void updateDefault(ProductSkuDTO productSkuDTO) {
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getId, productSkuDTO.getProductId()));
        if(product == null||product.getIsDeleted()) {
            log.error("商品不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        product.setProductSkuId(productSkuDTO.getId());
        productMapper.updateById(product);
    }

    @Override
    @Transactional
    public void updateConsume(Long skuId, Integer quantity) {
        ProductSku productSku = baseMapper.selectById(skuId);
        if(productSku == null||productSku.getIsDeleted()) {
            log.error("商品规格不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getId, productSku.getProductId()));
        if(product == null||product.getIsDeleted()) {
            log.error("商品不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //判断是否超库存
        if(productSku.getStock() < quantity) {
            log.error("商品规格库存不足");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //TODO 在更新规格库存接口也应该设置分布式锁
        RLock lock = redissonClient.getLock(RedisKeyConstant.LOCK_PRODUCT.key+skuId);
        boolean isLock = false;
        int retryCount = 0;
        try{
            // 循环重试间隔1秒总耗时3秒
            while(retryCount<3){
                isLock = lock.tryLock(0,10, TimeUnit.SECONDS);
                if(isLock)break;
                retryCount++;
                log.warn("第{}次获取锁失败（ID：{}），1秒后重试", retryCount, skuId);
                Thread.sleep(1000);
            }
            // 所有重试完成后仍未获取锁
            if (!isLock) {
                log.error("ID：{} 3秒内重试3次仍未获取锁，抢券失败", skuId);
                throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
            }
            // 获取锁成功
            int updateCount = baseMapper.updateStock(skuId, productSku.getVersion(),quantity);
            if (updateCount == 0) {
                log.error("商品规格消费并发冲突，id:{}", skuId);
                throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
            }
            updateCount = productMapper.updateStock(skuId,quantity);
            if (updateCount == 0) {
                log.error("商品消费并发冲突，id:{}", skuId);
                throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
            }
        }catch (InterruptedException e){
            // 处理线程中断异常,恢复中断状态
            log.error("重试过程中线程被中断（ID：{}）", skuId, e);
            Thread.currentThread().interrupt();
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }finally {
            // 安全释放锁：仅当当前线程持有锁时才解锁
            if (isLock && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("ID：{} 已释放分布式锁", skuId);
            }
        }

    }

    @Override
    public void updateLogo(ProductSkuDTO productSkuDTO) {
        ProductSku productSku = baseMapper.selectById(productSkuDTO.getId());
        if(productSku == null||productSku.getIsDeleted()) {
            log.error("商品规格不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        productSku.setLogo(productSkuDTO.getLogo());
        baseMapper.updateById(productSku);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductSku productSku = baseMapper.selectById(id);
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>().eq(Product::getId, productSku.getProductId()));
        if(product == null||product.getIsDeleted()){
            productSku.setIsDeleted(true);
            baseMapper.updateById(productSku);
            return;
        }
        //不能删除最后一个商品规格也不能删除默认商品规格
        long count = baseMapper.selectCount(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productSku.getProductId()));
        if(count == 1)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        if(Objects.equals(productSku.getId(), product.getProductSkuId()))
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        int productStock = product.getStock();
        product.setStock(product.getStock()-productSku.getStock());
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
                .eq(Product::getId, product.getId())
                .eq(Product::getStock, productStock)
                .eq(Product::getIsDeleted, false);
        int updateCount = productMapper.update(product, updateWrapper);
        //TODO 可用异步通信技术添加重试机制
        if (updateCount == 0)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        productSku.setIsDeleted(true);
        baseMapper.updateById(productSku);
    }
}
