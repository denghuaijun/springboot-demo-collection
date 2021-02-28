package com.example.springbootdemojdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringbootDemoJdbcApplicationTests {

    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        //查看数据源的默认配置//class com.zaxxer.hikari.HikariDataSource
        System.out.println("数据源使用==："+dataSource.getClass());
        //创建连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        //声明statement对象进行预编译
//        PreparedStatement preparedStatement = connection.prepareStatement("");
//        ResultSet resultSet = preparedStatement.executeQuery();
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
        connection.close();
    }

}
