package com.self.highperformance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// 需要配置使用Feign调用远程的内容
@EnableFeignClients(basePackages = {"com.self.highperformance.goods.feign"})
public class MallCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallCartApplication.class, args);
    }
}
