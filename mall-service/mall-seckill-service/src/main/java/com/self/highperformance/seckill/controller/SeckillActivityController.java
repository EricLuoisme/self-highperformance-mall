package com.self.highperformance.seckill.controller;

import com.self.highperformance.seckill.model.SeckillActivity;
import com.self.highperformance.seckill.service.SeckillActivityService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class SeckillActivityController {

    @Autowired
    private SeckillActivityService seckillActivityService;


    /**
     * 未过期的活动列表
     */
    @GetMapping
    public RespResult<List<SeckillActivity>> list() {
        //有效的活动时间查询
        List<SeckillActivity> list = seckillActivityService.validActivity();
        return RespResult.ok(list);
    }


}
