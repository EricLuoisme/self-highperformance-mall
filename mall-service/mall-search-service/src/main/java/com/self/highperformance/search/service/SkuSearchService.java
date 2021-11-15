package com.self.highperformance.search.service;

import com.self.highperformance.search.model.SkuEs;

import java.util.Map;

public interface SkuSearchService {

    /**
     * 添加索引
     * @param skuEs 实体
     */
    void addIndex(SkuEs skuEs);

    /**
     * 删除索引
     * @param id 主键id
     */
    void delIndex(String id);

    /**
     * 关键词搜索
     * @param searchMap
     * @return
     */
    Map<String, Object> search(Map<String, Object> searchMap);
}
