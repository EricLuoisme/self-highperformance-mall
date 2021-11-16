package com.self.highperformance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.self.highperformance.goods.feign")
public class MallPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallPageApplication.class, args);
    }
}
