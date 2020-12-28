package com.dhj.demo.neo4j.config;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

/**
 * neo4j图库配置信息加载类
 */
@Configuration
public class Neo4jConfig {

    @Bean
    public SessionFactory sessionFactory(){
        return new SessionFactory(configuration(),"com.dhj.demo.neo4j.domain");
    }
    @Bean
    public org.neo4j.ogm.config.Configuration configuration(){
        ConfigurationSource source = new ClasspathConfigurationSource("neo4j-ogm.properties");
        source.properties().setProperty("","");
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder(source).build();
        return configuration;
    }
    @Bean
    public Neo4jTransactionManager transactionManager(){
        return new Neo4jTransactionManager(sessionFactory());
    }

}
