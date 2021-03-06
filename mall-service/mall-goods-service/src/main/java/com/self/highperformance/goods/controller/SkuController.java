package com.self.highperformance.goods.controller;

import com.self.highperformance.cart.model.Cart;
import com.self.highperformance.goods.model.Sku;
import com.self.highperformance.goods.service.SkuService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 根据推广分类查询推广产品列表, 因为是响应给Nginx缓存, 不使用Resp封装
     */
    @GetMapping("/aditems/type")
    public List<Sku> typeItems(@RequestParam("id") Integer id) {
        return skuService.typeSkuItems(id);
    }

    /**
     * 更新缓存
     */
    @PutMapping("/aditems/type")
    public RespResult<List<Sku>> updTypeItems(@RequestParam("id") Integer id) {
        skuService.updTypeSkuItems(id);
        return RespResult.ok();
    }

    /**
     * 清除缓存
     */
    @DeleteMapping("/aditems/type")
    public RespResult delTypeItems(@RequestParam("id") Integer id) {
        skuService.delTypeSkuItems(id);
        return RespResult.ok();
    }

    /**
     * 根据商品id获取商品详情
     */
    @GetMapping("/{id}")
    public RespResult<Sku> findBySkuId(@PathVariable("id") String id) {
        Sku byId = skuService.getById(id);
        return RespResult.ok(byId);
    }

    /**
     * 库存递减
     */
    @PostMapping("/dcount")
    public RespResult dcount(@RequestBody List<Cart> carts) {
        skuService.dcount(carts);
        return RespResult.ok();
    }
}
