package com.example.springbootdemojdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.springbootdemojdbc.mapper")
@SpringBootApplication
public class SpringbootDemoJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoJdbcApplication.class, args);
    }

}
