package com.self.highperformance.search.mapper;

import com.self.highperformance.search.model.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SkuSearchMapper extends ElasticsearchRepository<SkuEs, String> {
}
