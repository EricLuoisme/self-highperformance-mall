package com.self.highperformance.seckill.controller;

import com.self.highperformance.seckill.model.SeckillGoods;
import com.self.highperformance.seckill.service.SeckillGoodsService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/seckill/goods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /***
     * 根据活动查询秒杀商品集合
     * @param acid
     * @return
     */
    @GetMapping(value = "/act/{acid}")
    public RespResult<List<SeckillGoods>> actGoods(@PathVariable("acid") String acid){
        List<SeckillGoods> seckillGoods = seckillGoodsService.actGoods(acid);
        return RespResult.ok(seckillGoods);
    }

    /***
     * 根据ID查询秒杀商品详情
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public RespResult<SeckillGoods> one(@PathVariable("id") String id){
        SeckillGoods seckillGoods = seckillGoodsService.getById(id);
        return RespResult.ok(seckillGoods);
    }

}
