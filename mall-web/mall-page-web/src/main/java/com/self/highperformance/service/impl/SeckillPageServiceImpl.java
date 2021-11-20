package com.self.highperformance.service.impl;

import com.self.highperformance.seckill.feign.SeckillGoodsFeign;
import com.self.highperformance.seckill.model.SeckillGoods;
import com.self.highperformance.service.SeckillPageService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/*****
 * @Author:
 * @Description:
 ****/
@Service
public  class SeckillPageServiceImpl implements SeckillPageService {

    @Value("${seckillpath}")
    private String seckillpath;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private SeckillGoodsFeign seckillGoodsFeign;


    /**
     * 生成秒杀详情页静态页
     * @param id
     * @throws Exception
     */
    @Override
    public void html(String id) throws Exception {
        // 1、创建容器对象(上下文对象)
        Context context = new Context();
        // 2、设置模板数据  loadData(id) -> Map
        context.setVariables(loadData(id));
        // 3、指定文件生成后存储路径
        File file = new File(seckillpath,id+".html");
        PrintWriter writer = new PrintWriter(file,"UTF-8");
        // 4、执行合成生成
        templateEngine.process("seckillitem",context,writer);
    }

    @Override
    public void delete(String id) {
        // 创建要删除的文件对象
        File file = new File(seckillpath,id+".html");
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 数据加载
     */
    public Map<String,Object> loadData(String id){
        // 查询数据
        RespResult<SeckillGoods> goodsResp = seckillGoodsFeign.one(id);
        if(goodsResp.getData()!=null){
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("item",goodsResp.getData());
            dataMap.put("images",goodsResp.getData().getImages().split(","));
            return dataMap;
        }
        return null;
    }
}
