package com.self.highperformance.search.service.impl;

import com.self.highperformance.search.mapper.SeckillGoodsSearchMapper;
import com.self.highperformance.search.model.SeckillGoodsEs;
import com.self.highperformance.search.service.SeckillGoodsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillGoodsSearchServiceImpl implements SeckillGoodsSearchService {

    @Autowired
    private SeckillGoodsSearchMapper seckillGoodsSearchMapper;


    @Override
    public void add(SeckillGoodsEs es) {
        seckillGoodsSearchMapper.save(es);
    }
}
