package com.self.highperformance;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = "com.self.highperformance.order.mapper")
@EnableFeignClients(basePackages = {"com.self.highperformance.goods.feign", "com.self.highperformance.cart.feign"})
public class MallOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class, args);
    }
}
