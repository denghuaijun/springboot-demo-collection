package com.dhj.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

/**
 * springboot启动类
 */
@ComponentScan(basePackages = {"com.dhj.demo.springconfig"})
@SpringBootApplication
public class MybatisPlusApplication {
    public static void main(String[] args) throws IOException {
        //可选配置启动环境参数配置
        //-Dconf-path.env=xxxxxx -Dconf-path.system=XXXXXX -Denv=(PROD\DEV\TEST)
       // Env.initLaunchVariable();
        SpringApplication.run(MybatisPlusApplication.class,args);
    }
}
