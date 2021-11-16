package com.self.highperformance.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.highperformance.goods.mapper.BrandMapper;
import com.self.highperformance.goods.mapper.CategoryMapper;
import com.self.highperformance.goods.mapper.SkuMapper;
import com.self.highperformance.goods.mapper.SpuMapper;
import com.self.highperformance.goods.model.*;
import com.self.highperformance.goods.service.SpuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;


    @Override
    public void saveProduct(Product product) {
        // 1. 保存Spu
        Spu spu = product.getSpu();
        spu.setIsMarketable(1);
        spu.setIsDelete(0);
        spu.setStatus(1);
        if (StringUtils.isEmpty(spu.getId())) {
            // 为空证明是插入
            spuMapper.insert(spu);
        } else {
            // 不为空则是修改
            spuMapper.updateById(spu);
            // 删除sku集合
            skuMapper.delete(new QueryWrapper<Sku>().eq("spu_id", spu.getId()));
        }

        // 2. 保存List<Sku>
        List<Sku> skus = product.getSkus();
        Date date = new Date();
        Category category = categoryMapper.selectById(spu.getCategoryThreeId());
        Brand brand = brandMapper.selectById(spu.getBrandId());

        for (Sku sku : skus) {
            // 循环替换名称
            StringBuilder name = new StringBuilder(sku.getName());
            Map<String, String> skuAttriMap = JSON.parseObject(sku.getSkuAttribute(), Map.class);
            for (Map.Entry<String, String> entry : skuAttriMap.entrySet()) {
                name.append(" ").append(entry.getValue());
            }
            sku.setName(name.toString());
            // 设置额外字段填充
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setCategoryId(spu.getCategoryThreeId());
            sku.setCategoryName(category.getName());
            sku.setBrandId(spu.getBrandId());
            sku.setBrandName(brand.getName());
            sku.setSpuId(spu.getId());
            sku.setStatus(1);
            // 存入db
            skuMapper.insert(sku);
        }
    }

    @Override
    public Product findBySpuId(String id) {

        // 1. 查询spu
        Spu spu = spuMapper.selectById(id);

        // 2. 根据spu的id, 查询相关的sku集合
        List<Sku> skus = skuMapper.selectList(
                new QueryWrapper<Sku>().eq("spu_id", id));

        // 3. 组装返回的Product对象
        return new Product().setSpu(spu).setSkus(skus);
    }
}
