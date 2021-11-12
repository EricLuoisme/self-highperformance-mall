package com.self.highperformance.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.goods.model.Sku;

import java.util.List;

public interface SkuService extends IService<Sku> {

    List<Sku> typeSkuItems(Integer id);

    void delTypeSkuItems(Integer id);
}
