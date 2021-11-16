package com.self.highperformance.service.impl;

import com.alibaba.fastjson.JSON;
import com.self.highperformance.goods.feign.CategoryFeign;
import com.self.highperformance.goods.feign.SpuFeign;
import com.self.highperformance.goods.model.Category;
import com.self.highperformance.goods.model.Product;
import com.self.highperformance.goods.model.Spu;
import com.self.highperformance.service.PageService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private CategoryFeign categoryFeign;

    @Autowired
    private SpuFeign spuFeign;

    @Value("${pagePath}")
    private String pagePath;


    @Override
    public void html(String spuId) throws FileNotFoundException, UnsupportedEncodingException {

        // 1. 创建容器对象(上下文)
        Context context = new Context();

        // 2. 设置template的数据
        context.setVariables(loadData(spuId));

        // 3. 指定文件生成后存储路径
        File file = new File(pagePath, spuId + ".html");
        PrintWriter writer = new PrintWriter(file, "UTF-8");

        // 4. 执行合成生成
        templateEngine.process("item", context, writer);
    }

    /**
     * 数据加载到Map
     */
    private Map<String, Object> loadData(String spuId) {
        // 调用Feign接口获取Product实体
        RespResult<Product> resp = spuFeign.one(spuId);
        Product product = resp.getData();
        if (null != product) {
            // Map
            HashMap<String, Object> resultMap = new HashMap<>();
            // Spu
            Spu spu = product.getSpu();
            resultMap.put("spu", spu);
            // 图片获取
            resultMap.put("images", spu.getImages().split(","));
            // Attributes列表
            resultMap.put("attrs", JSON.parseObject(spu.getAttributeList(), Map.class));
            // 三级分类
            RespResult<Category> one = categoryFeign.searchOne(spu.getCategoryOneId());
            RespResult<Category> two = categoryFeign.searchOne(spu.getCategoryTwoId());
            RespResult<Category> three = categoryFeign.searchOne(spu.getCategoryThreeId());
            resultMap.put("one", one.getData());
            resultMap.put("two", two.getData());
            resultMap.put("three", three.getData());
            // sku集合
            List<Map<String, Object>> skuList = new ArrayList<>();
            product.getSkus().forEach(sku -> {
                Map<String, Object> skuMap = new HashMap<>();
                skuMap.put("id", sku.getId());
                skuMap.put("name", sku.getId());
                skuMap.put("price", sku.getId());
                skuMap.put("attr", sku.getId());
                // 添加到list
                skuList.add(skuMap);
            });
            resultMap.put("skuList", skuList);
            // 结束数据组装
            return resultMap;
        }
        // 无数据返回null
        return null;
    }
}
