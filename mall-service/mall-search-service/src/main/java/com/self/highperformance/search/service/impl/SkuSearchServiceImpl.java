package com.self.highperformance.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.self.highperformance.search.mapper.SkuSearchMapper;
import com.self.highperformance.search.model.SkuEs;
import com.self.highperformance.search.service.SkuSearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkuSearchServiceImpl implements SkuSearchService {

    @Autowired
    private SkuSearchMapper skuSearchMapper;


    @Override
    public void addIndex(SkuEs skuEs) {
        // 将Json格式属性转换为map, 方便es子类查询
        String skuAttribute = skuEs.getSkuAttribute();
        if (StringUtils.isNotEmpty(skuAttribute)) {
            skuEs.setAttrMap(JSON.parseObject(skuAttribute, Map.class));
        }
        skuSearchMapper.save(skuEs);
    }

    @Override
    public void delIndex(String id) {
        skuSearchMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {

        // 构建搜索对象
        NativeSearchQueryBuilder queryBuilder = getQueryBuilder(searchMap);

        // Mapper进行搜索
        Page<SkuEs> search = skuSearchMapper.search(queryBuilder.build());

        // 获取结果集
        HashMap<String, Object> resultMap = new HashMap<>();
        List<SkuEs> content = search.getContent();
        resultMap.put("list", content);
        resultMap.put("totalElements", search.getTotalElements());
        return resultMap;
    }

    private NativeSearchQueryBuilder getQueryBuilder(Map<String, Object> searchMap) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        if (null != searchMap && searchMap.size() > 0) {
            Object keywords = searchMap.get("keywords");
            if (StringUtils.isNotEmpty((String) keywords)) {
                queryBuilder.withQuery(QueryBuilders.termQuery("name", keywords));
            }
        }
        return queryBuilder;
    }


}
