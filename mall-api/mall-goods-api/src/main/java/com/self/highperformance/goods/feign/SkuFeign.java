package com.self.highperformance.goods.feign;

import com.self.highperformance.goods.model.Sku;
import com.self.highperformance.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign接口, 对应Sku服务的路径
 */
@FeignClient("mall-goods")
public interface SkuFeign {

    /**
     * 根据推广分类查询推广产品列表, 因为是响应给Nginx缓存, 不使用Resp封装
     */
    @GetMapping("/sku/aditems/type")
    List<Sku> typeItems(@RequestParam("id") Integer id);

    /**
     * 更新缓存
     */
    @PutMapping("/sku/aditems/type")
    RespResult<List<Sku>> updTypeItems(@RequestParam("id") Integer id);

    /**
     * 清除缓存
     */
    @DeleteMapping("/sku/aditems/type")
    RespResult delTypeItems(@RequestParam("id") Integer id);

    /**
     * skuId找到sku
     */
    @GetMapping("/sku/{id}")
    RespResult<Sku> findBySkuId(@PathVariable("id") String id);

}
