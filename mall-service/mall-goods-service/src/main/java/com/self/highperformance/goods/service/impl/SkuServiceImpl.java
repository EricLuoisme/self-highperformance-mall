package com.self.highperformance.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.highperformance.cart.model.Cart;
import com.self.highperformance.goods.mapper.AdItemsMapper;
import com.self.highperformance.goods.mapper.SkuMapper;
import com.self.highperformance.goods.model.AdItems;
import com.self.highperformance.goods.model.Sku;
import com.self.highperformance.goods.service.SkuService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Autowired
    private AdItemsMapper adItemsMapper;

    @Autowired
    private SkuMapper skuMapper;


    // 添加本地回滚
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dcount(List<Cart> carts) {
        /**
         * 需要进行循环操作, 因为批量操作某个操作受影响行数为0也不会继续
         */
        for (Cart cart : carts) {
            int dcount = skuMapper.dcount(cart.getSkuId(), cart.getNum());
            if (dcount <= 0) {
                throw new RuntimeException("Out Of Stock");
            }
            System.out.println("Sku:" + cart.getSkuId() + "'s Stock decreased to " + dcount);
        }
    }

    // 开启缓存, 一级缓存命名空间为ad-items-skus, 可以使用id
    @Cacheable(cacheNames = "ad-items-skus", key = "#id")
    @Override
    public List<Sku> typeSkuItems(Integer id) {
        // 1. 查询当前分类下所有列表信息
        List<AdItems> adItems = adItemsMapper.selectList(new QueryWrapper<AdItems>().eq("type", id));
        List<String> skuIds = adItems.stream().map(AdItems::getSkuId).collect(Collectors.toList());
        // 2. 根据列表查询商品列表
        return skuIds.isEmpty() ? null : skuMapper.selectBatchIds(skuIds);
    }

    @CachePut(cacheNames = "ad-items-skus", key = "#id")
    @Override
    public List<Sku> updTypeSkuItems(Integer id) {
        // 1. 查询当前分类下所有列表信息
        List<AdItems> adItems = adItemsMapper.selectList(new QueryWrapper<AdItems>().eq("type", id));
        List<String> skuIds = adItems.stream().map(AdItems::getSkuId).collect(Collectors.toList());
        // 2. 根据列表查询商品列表
        return skuIds.isEmpty() ? null : skuMapper.selectBatchIds(skuIds);
    }

    @CacheEvict(cacheNames = "ad-items-skus", key = "#id")
    @Override
    public void delTypeSkuItems(Integer id) {
        // 仅删除缓存
    }
}
