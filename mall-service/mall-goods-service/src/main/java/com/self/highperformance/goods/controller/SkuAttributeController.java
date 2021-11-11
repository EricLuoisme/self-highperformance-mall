package com.self.highperformance.goods.controller;

import com.self.highperformance.goods.model.SkuAttribute;
import com.self.highperformance.goods.service.SkuAttributeService;
import com.self.highperformance.resp.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/skuAttribute")
public class SkuAttributeController {

    @Autowired
    private SkuAttributeService skuAttributeService;


    /**
     * 根据分类id查询属性集合
     */
    @GetMapping("/category/{id}")
    public RespResult<List<SkuAttribute>> categorySkuAttributeList(@PathVariable("id") Integer id) {
        List<SkuAttribute> skuAttributes = skuAttributeService.queryList(id);
        return RespResult.ok(skuAttributes);
    }


}
