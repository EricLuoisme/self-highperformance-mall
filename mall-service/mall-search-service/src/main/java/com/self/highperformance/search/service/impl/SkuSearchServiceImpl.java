package com.self.highperformance.search.service.impl;

import com.self.highperformance.search.mapper.SkuSearchMapper;
import com.self.highperformance.search.model.SkuEs;
import com.self.highperformance.search.service.SkuSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuSearchServiceImpl implements SkuSearchService {

    @Autowired
    private SkuSearchMapper skuSearchMapper;


    @Override
    public void addIndex(SkuEs skuEs) {
        skuSearchMapper.save(skuEs);
    }

    @Override
    public void delIndex(String id) {
        skuSearchMapper.deleteById(id);
    }
}
