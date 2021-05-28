
package com.dhj.demo.business.config.click;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源配置类
 * 本类从pe_conf_iitem表中读取配置,然后可以配置CarbonData,ClickHouse,mysql的数据源
 * 数据源并没有被放到容器,而是作为本类的一个类成员,可以被随意访问
 * <p>
 * 启动时报错的问题: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean
 * with name 'bigDataJdbcTemplateConfig': Unsatisfied dependency expressed through field
 * 'hiveDataSourceConfig'; nested exception is
 * org.springframework.beans.factory.BeanCreationException: Error creating bean with name
 * 'bigDataSourceConfig': Invocation of init method failed; nested exception is
 * org.mybatis.spring.MyBatisSystemException: nested exception is
 * org.apache.ibatis.reflection.ReflectionException: There is no getter for property named
 * 'statement' in 'class org.apache.tomcat.jdbc.pool.StatementFacade$StatementProxy'
 * https://blog.csdn.net/wangjx92/article/details/80296546
 * https://gitee.com/baomidou/mybatisplus-spring-boot/issues/II55O
 * 症状是,有时改了一下配置文件中的日志的配置,就会导致启动报错: There is no getter for property named 'statement' ... ...
 * <p>
 * 如果把@PostConstruct中的所有代码全删掉,则本类不报错了,墨渊写的PermissionMap类又报错了
 * 原因是这两个类中都在@PostConstruct中使用了mapper
 * <p>
 * 我感觉,这极有可能是mybatis-plus的bug,它好像和springboot1.5.x自带的数据源有点小冲突
 * <p>
 * 所以只要它不报错,就先暂时不处理了
 */
@Service
public class ClickHouseSourceConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClickHouseSourceConfig.class);

    private ClickhouseConfigProperties clickhouseConfigProperties;

    private DataSource bigDataSource;

    private String database;

    private Map<String, JdbcTemplate> jdbcTemplateMap = new HashMap<>();

    public ClickHouseSourceConfig(ClickhouseConfigProperties clickhouseConfigProperties) {
        this.clickhouseConfigProperties = clickhouseConfigProperties;
        initDataSource();
    }

    public DataSource getBigDataSource() {
        return bigDataSource;
    }

    private void initDataSource() {
        String url = clickhouseConfigProperties.getUrl();
        BalancedClickhouseDataSource balancedClickhouseDataSource = new BalancedClickhouseDataSource(url, createClickHouseProperties());
        database = balancedClickhouseDataSource.getProperties().getDatabase();
        List<String> allClickhouseUrls = balancedClickhouseDataSource.getAllClickhouseUrls();
        Boolean useLoadbalancer = clickhouseConfigProperties.getUseLoadbalancer();
        if (useLoadbalancer && allClickhouseUrls.size() > 1) {
            bigDataSource = balancedClickhouseDataSource;
        } else {
            bigDataSource = new ClickHouseDataSource(allClickhouseUrls.get(0), createClickHouseProperties());
        }
        for (String jdbcUrl : allClickhouseUrls) {
            ClickHouseDataSource clickHouseDataSource = new ClickHouseDataSource(jdbcUrl, createClickHouseProperties());
            JdbcTemplate jdbcTemplate = new JdbcTemplate(clickHouseDataSource);
            String host = clickHouseDataSource.getHost();
            jdbcTemplateMap.put(host, jdbcTemplate);
        }
        LOGGER.info("clickhouse 数据源初始化完成,当前连接类型");
    }

    private ClickHouseProperties createClickHouseProperties() {
        ClickHouseProperties prop = new ClickHouseProperties();
        prop.setPassword(clickhouseConfigProperties.getPassword());
        prop.setUser(clickhouseConfigProperties.getUser());
        return prop;
    }

    public Map<String, JdbcTemplate> getJdbcTemplateMap() {
        return this.jdbcTemplateMap;
    }

    public String getDatabase() {
        return database;
    }

}
