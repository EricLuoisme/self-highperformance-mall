package com.self.highperformance.listener;

import com.self.highperformance.goods.feign.SkuFeign;
import com.self.highperformance.goods.model.AdItems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * 监听AdItems表的变更
 */
@CanalTable(value = "ad_items")
@Component
@Slf4j
public class AdItemsHandler implements EntryHandler<AdItems> {

    @Autowired
    private SkuFeign skuFeign;


    @Override
    public void insert(AdItems adItems) {
        System.out.println("Call SkuFeign Insert Update");
        skuFeign.updTypeItems(adItems.getType());
    }


    /**
     * 新版本的update中before仅包含变更的属性的上一个值, 故没必要对原type还进行一次update
     */
    @Override
    public void update(AdItems before, AdItems after) {
        System.out.println("Call SkuFeign Update");
        skuFeign.updTypeItems(after.getType());
    }

    @Override
    public void delete(AdItems adItems) {
        System.out.println("Call SkuFeign Delete");
        skuFeign.delTypeItems(adItems.getType());
    }
}