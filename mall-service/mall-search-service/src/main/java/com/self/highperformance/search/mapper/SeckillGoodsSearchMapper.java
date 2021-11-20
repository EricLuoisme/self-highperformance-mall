package com.self.highperformance.search.mapper;

import com.self.highperformance.search.model.SeckillGoodsEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SeckillGoodsSearchMapper extends ElasticsearchRepository<SeckillGoodsEs, String> {
}
