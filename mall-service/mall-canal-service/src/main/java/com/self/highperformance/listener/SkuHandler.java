package com.self.highperformance.listener;

import com.alibaba.fastjson.JSON;
import com.self.highperformance.goods.model.Sku;
import com.self.highperformance.search.feign.SkuSearchFeign;
import com.self.highperformance.search.model.SkuEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@Component
@CanalTable("sku")
public class SkuHandler implements EntryHandler<Sku> {

    @Autowired
    private SkuSearchFeign skuSearchFeign;


    @Override
    public void insert(Sku sku) {
        System.out.println("Sku Index Add Update");
        // 1 代表商品状态正常
        if (1 == sku.getStatus()) {
            // 先把sku转换为JsonStr, 然后通过JsonStr转换为SkuEs对象
            skuSearchFeign.add(JSON.parseObject(JSON.toJSONString(sku), SkuEs.class));
        }
    }

    @Override
    public void update(Sku before, Sku after) {
        System.out.println("Sku Index Update");
        // 2 代表商品下架
        if (2 == after.getStatus()) {
            skuSearchFeign.del(after.getId());
        }
        // 如果商品状态仍然正常, 则add以实现更新
        if (1 == after.getStatus()) {
            skuSearchFeign.add(JSON.parseObject(JSON.toJSONString(after), SkuEs.class));
        }
    }

    @Override
    public void delete(Sku sku) {
        System.out.println("Sku Index Delete");
        skuSearchFeign.del(sku.getId());
    }
}
