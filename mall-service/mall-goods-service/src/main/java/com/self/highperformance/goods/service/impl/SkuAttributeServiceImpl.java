package com.self.highperformance.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.highperformance.goods.mapper.SkuAttributeMapper;
import com.self.highperformance.goods.model.SkuAttribute;
import com.self.highperformance.goods.service.SkuAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuAttributeServiceImpl extends ServiceImpl<SkuAttributeMapper, SkuAttribute> implements SkuAttributeService {

    @Autowired
    private SkuAttributeMapper mapper;


    @Override
    public List<SkuAttribute> queryList(Integer id) {
        return mapper.queryByCategoryId(id);
    }
}
