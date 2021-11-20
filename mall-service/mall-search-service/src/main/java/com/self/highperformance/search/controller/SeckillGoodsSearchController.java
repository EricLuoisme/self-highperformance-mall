package com.self.highperformance.search.controller;

import com.self.highperformance.search.model.SeckillGoodsEs;
import com.self.highperformance.search.service.SeckillGoodsSearchService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/seckill/goods")
public class SeckillGoodsSearchController {

    @Autowired
    private SeckillGoodsSearchService service;


    @PostMapping("/import")
    public RespResult add(@RequestBody SeckillGoodsEs es) {
        service.add(es);
        return RespResult.ok();
    }

}
