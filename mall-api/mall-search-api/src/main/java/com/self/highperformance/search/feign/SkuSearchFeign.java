package com.self.highperformance.search.feign;

import com.self.highperformance.resp.RespResult;
import com.self.highperformance.search.model.SkuEs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-search")
public interface SkuSearchFeign {

    @PostMapping("/search/add")
    RespResult add(@RequestBody SkuEs skuEs);

    @DeleteMapping("/search/del/{id}")
    RespResult del(@PathVariable("id") String id);

}
