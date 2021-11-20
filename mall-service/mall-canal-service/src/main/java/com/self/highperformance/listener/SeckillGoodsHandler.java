package com.self.highperformance.listener;

import com.alibaba.fastjson.JSON;
import com.self.highperformance.page.feign.SeckillPageFeign;
import com.self.highperformance.search.feign.SeckillGoodsSearchFeign;
import com.self.highperformance.search.model.SeckillGoodsEs;
import com.self.highperformance.seckill.model.SeckillGoods;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;


@CanalTable(value = "seckill_goods")
@Component
public class SeckillGoodsHandler implements EntryHandler<SeckillGoods> {

    @Autowired
    private SeckillGoodsSearchFeign seckillGoodsSearchFeign;

    @Autowired
    private SeckillPageFeign seckillPageFeign;

    /***
     * 增加商品
     * @param seckillGoods
     */
    @SneakyThrows
    @Override
    public void insert(SeckillGoods seckillGoods) {

        SeckillGoodsEs seckillGoodsEs = beanConvert(seckillGoods);

        // 索引导入
        seckillGoodsSearchFeign.add(seckillGoodsEs);

        // 生成/更新静态页
        seckillPageFeign.page(seckillGoods.getId());
    }


    /****
     * 修改商品
     * @param before
     * @param after
     */
    @SneakyThrows
    @Override
    public void update(SeckillGoods before, SeckillGoods after) {

        SeckillGoodsEs seckillGoodsEs = beanConvert(after);

        // 索引导入
        seckillGoodsSearchFeign.add(seckillGoodsEs);

        // 生成/更新静态页
        seckillPageFeign.page(after.getId());
    }

    /**
     * 由于莫名的seckillPrice指定了列名, Integer转换会带.0导致问题, 暂时方便直接转换
     */
    private SeckillGoodsEs beanConvert(SeckillGoods goods) {
        SeckillGoodsEs es = new SeckillGoodsEs();
        es.setId(goods.getId());
        es.setSupId(goods.getSupId());
        es.setSkuId(goods.getSkuId());
        es.setName(goods.getName());
        es.setImages(goods.getImages());
        es.setPrice(Integer.valueOf(goods.getPrice()));
        String seckillPrice = goods.getSeckillPrice();
        seckillPrice = seckillPrice.substring(0, seckillPrice.length() - 2);
        es.setSeckillPrice(Integer.valueOf(seckillPrice));
        es.setNum(goods.getNum());
        es.setStoreCount(goods.getStoreCount());
        es.setActivityId(goods.getActivityId());
        return es;
    }
}
