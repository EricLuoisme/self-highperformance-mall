package com.self.highperformance.search.controller;

import com.self.highperformance.resp.RespResult;
import com.self.highperformance.search.model.SkuEs;
import com.self.highperformance.search.service.SkuSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SkuSearchController {

    @Autowired
    private SkuSearchService skuSearchService;


    @PostMapping("/add")
    public RespResult add(@RequestBody SkuEs skuEs) {
        skuSearchService.addIndex(skuEs);
        return RespResult.ok();
    }

    @DeleteMapping("/del/{id}")
    public RespResult del(@PathVariable("id") String id) {
        skuSearchService.delIndex(id);
        return RespResult.ok();
    }
}
