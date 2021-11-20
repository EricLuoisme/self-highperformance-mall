package com.self.highperformance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// @EnableScheduling
// 需要配置使用Feign调用远程的内容
@EnableFeignClients(basePackages = {
        "com.self.highperformance.page.feign",
        "com.self.highperformance.goods.feign",
        "com.self.highperformance.search.feign",
        "com.self.highperformance.seckill.feign"})
public class MallCanalApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallCanalApplication.class, args);
    }
}
