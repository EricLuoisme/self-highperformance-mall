package com.self.highperformance.goods.feign;

import com.self.highperformance.goods.model.Category;
import com.self.highperformance.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("mall-goods")
public interface CategoryFeign {

    @GetMapping("/category/parent/{pid}")
    RespResult<List<Category>> list(@PathVariable("pid") Integer pid);

    @GetMapping("/category/{id}")
    RespResult<Category> searchOne(@PathVariable("id") Integer id);
}
