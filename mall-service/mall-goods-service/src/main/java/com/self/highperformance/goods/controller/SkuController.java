package com.self.highperformance.goods.controller;

import com.self.highperformance.goods.model.Sku;
import com.self.highperformance.goods.service.SkuService;
import com.self.highperformance.resp.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 根据推广分类查询推广产品列表
     */
    @GetMapping("/aditems/type")
    public RespResult<List<Sku>> typeItems(@RequestParam("id") Integer id) {
        List<Sku> skus = skuService.typeSkuItems(id);
        return RespResult.ok(skus);
    }


    /**
     * 清除缓存
     */
    @DeleteMapping("/aditems/type")
    public RespResult delTypeItems(@RequestParam("id") Integer id) {
        skuService.delTypeSkuItems(id);
        return RespResult.ok();
    }
}
