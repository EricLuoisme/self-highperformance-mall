package com.self.highperformance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.self.highperformance.user.mapper")
public class MallUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallUserApplication.class, args);
    }
}
