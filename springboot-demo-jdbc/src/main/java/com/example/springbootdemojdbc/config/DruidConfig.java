package com.example.springbootdemojdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 由于使用的是alibaba的driud数据源所以和mybaties-plus-demo模块中的druid不同的是，这个需要自己配置才能生效
 * 参考博客：https://www.cnblogs.com/hellokuangshen/p/12497041.html
 */
@Configuration
public class DruidConfig {
    /*
          将自定义的 Druid数据源添加到容器中，不再让 Spring Boot 自动创建
          绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效
          @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
          前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
        */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
    //druid后台监控，相当于一个web.xml，springboot已经将servlet内嵌在其中
    //访问方式：http://localhost:10101/druid/
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //后台需要有人登陆，账号密码配置参数
        HashMap<String, String> initParamMap = new HashMap<>();
        initParamMap.put("loginUsername","admin");
        initParamMap.put("loginPassword","admin");
        //允许谁可以访问，若为空则都可以访问,或者可以指定具体的IP或者人访问
        initParamMap.put("allow","");
        //或者写的具体就是禁止谁能访问
        //initParamMap.put("denghuaijun","禁止访问的IP");

        servletRegistrationBean.setInitParameters(initParamMap);
        return servletRegistrationBean;
    }
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        //exclusions：设置哪些请求进行过滤排除掉，从而不进行统计
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*,/jdbc/*");
        bean.setInitParameters(initParams);

        //"/*" 表示过滤所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;
    }
}
