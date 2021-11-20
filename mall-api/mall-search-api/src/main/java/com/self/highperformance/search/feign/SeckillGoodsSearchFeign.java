package com.self.highperformance.search.feign;

import com.self.highperformance.search.model.SeckillGoodsEs;
import com.self.highperformance.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-search")
public interface SeckillGoodsSearchFeign {

    @PostMapping("/seckill/goods/import")
    RespResult add(@RequestBody SeckillGoodsEs es);
}
