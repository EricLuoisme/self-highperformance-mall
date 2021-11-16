package com.self.highperformance.search.feign;

import com.self.highperformance.util.RespResult;
import com.self.highperformance.search.model.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient("mall-search")
public interface SkuSearchFeign {

    @PostMapping("/search/add")
    RespResult add(@RequestBody SkuEs skuEs);

    @DeleteMapping("/search/del/{id}")
    RespResult del(@PathVariable("id") String id);

    @GetMapping("/search")
    RespResult<Map<String, Object>> search(@RequestParam(required = false) Map<String, Object> searchMap);

}
