package com.self.highperformance.service;


public interface SeckillPageService {

    // 秒杀详情页生成
    void html(String id) throws Exception;

    // 删除秒杀详情页
    void delete(String id);
}
