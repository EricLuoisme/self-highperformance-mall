package com.self.highperformance.goods.controller;


import com.self.highperformance.goods.model.Product;
import com.self.highperformance.goods.service.SpuService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/spu")
@CrossOrigin
public class SpuController {

    @Autowired
    private SpuService spuService;

    /**
     * 产品保存
     */
    @PostMapping(value = "/save")
    public RespResult save(@RequestBody Product product) {
        spuService.saveProduct(product);
        return RespResult.ok();
    }

    /**
     * 查询Product
     */
    @GetMapping(value = "/product/{id}")
    public RespResult<Product> one(@PathVariable(value = "id") String id) {
        Product product = spuService.findBySpuId(id);
        return RespResult.ok(product);
    }
}
