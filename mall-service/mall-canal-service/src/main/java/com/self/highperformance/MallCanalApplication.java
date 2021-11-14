package com.self.highperformance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
// 需要配置使用Feign调用远程的内容
@EnableFeignClients(basePackages = {"com.self.highperformance.goods.feign"})
public class MallCanalApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallCanalApplication.class, args);
    }
}
