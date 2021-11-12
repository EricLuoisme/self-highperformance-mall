package com.self.highperformance.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.highperformance.goods.mapper.AdItemsMapper;
import com.self.highperformance.goods.mapper.SkuMapper;
import com.self.highperformance.goods.model.AdItems;
import com.self.highperformance.goods.model.Sku;
import com.self.highperformance.goods.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Autowired
    private AdItemsMapper adItemsMapper;

    @Autowired
    private SkuMapper skuMapper;

    // 开启缓存, 一级缓存命名空间为ad-items-skus, 可以使用id
    @Cacheable(cacheNames = "ad-items-skus", key = "#id")
    @Override
    public List<Sku> typeSkuItems(Integer id) {
        System.out.println("查询数据库");
        // 1. 查询当前分类下所有列表信息
        List<AdItems> adItems = adItemsMapper.selectList(new QueryWrapper<AdItems>().eq("type", id));
        List<String> skuIds = adItems.stream().map(x -> x.getSkuId()).collect(Collectors.toList());
        // 2. 根据列表查询商品列表
        return null == skuIds ? null : skuMapper.selectBatchIds(skuIds);
    }

    @CacheEvict(cacheNames = "ad-items-skus", key = "#id")
    @Override
    public void delTypeSkuItems(Integer id) {
        // 仅删除缓存
    }
}
