package com.self.highperformance.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.goods.model.Product;
import com.self.highperformance.goods.model.Spu;

public interface SpuService extends IService<Spu> {

    /**
     * 新建商品
     */
    void saveProduct(Product product);

    Product findBySpuId(String id);
}
