package com.self.highperformance.goods.feign;

import com.self.highperformance.goods.model.Product;
import com.self.highperformance.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-goods")
public interface SpuFeign {

    /**
     * 产品保存
     */
    @PostMapping(value = "/spu/save")
    RespResult save(@RequestBody Product product);

    /**
     * 查询Product
     */
    @GetMapping(value = "/spu/product/{id}")
    RespResult<Product> one(@PathVariable(value = "id") String id);

}
