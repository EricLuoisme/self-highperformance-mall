package com.self.highperformance.goods.controller;

import com.self.highperformance.goods.model.Product;
import com.self.highperformance.goods.service.SpuService;
import com.self.highperformance.resp.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private SpuService spuService;


    @PostMapping("/save")
    public RespResult saveProduct(@RequestBody Product product) {
        spuService.saveProduct(product);
        return RespResult.ok();
    }


}
