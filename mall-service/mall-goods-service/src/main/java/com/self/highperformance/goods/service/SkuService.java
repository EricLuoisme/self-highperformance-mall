package com.self.highperformance.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.goods.model.Sku;

import java.util.List;

public interface SkuService extends IService<Sku> {

    /**
     * 列表查询
     */
    List<Sku> typeSkuItems(Integer id);

    /**
     * 缓存更新
     */
    List<Sku> updTypeSkuItems(Integer id);

    /**
     * 缓存删除
     */
    void delTypeSkuItems(Integer id);
}
