
package com.dhj.demo.business.config.click;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 使用JdbcTemplate来操作clickhouse
 */
@Configuration
public class BigDataJdbcTemplateConfig {

    @Autowired
    ClickHouseSourceConfig hiveDataSourceConfig;

    @Bean
    public JdbcTemplate hiveJdbcTemplate() {
        return new JdbcTemplate(hiveDataSourceConfig.getBigDataSource());
    }
}
