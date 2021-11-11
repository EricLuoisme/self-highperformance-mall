package com.self.highperformance.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.goods.model.SkuAttribute;

import java.util.List;

public interface SkuAttributeService extends IService<SkuAttribute> {

    /**
     * 根据分类Id查询属性集合
     */
    List<SkuAttribute> queryList(Integer id);

}
