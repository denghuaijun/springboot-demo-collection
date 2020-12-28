package com.dhj.demo.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan(basePackages = "com.dhj.demo.neo4j.domain")
@EnableNeo4jRepositories(basePackages = "com.dhj.demo.neo4j.repository")
public class AppNeo4j {
    public static void main(String[] args) {
        SpringApplication.run(AppNeo4j.class,args);
    }
}
