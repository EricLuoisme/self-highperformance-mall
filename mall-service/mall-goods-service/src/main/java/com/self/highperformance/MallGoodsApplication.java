package com.self.highperformance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.self.highperformance.goods.mapper")
// 开启缓存, 并且需要在yml配置中进行配置, 若配置了redis就使用redis作为缓存
// 具体使用, 可以在某个方法上添加@Cachable, 作为为调用该接口将会被缓存
@EnableCaching
public class MallGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGoodsApplication.class, args);
    }
}
