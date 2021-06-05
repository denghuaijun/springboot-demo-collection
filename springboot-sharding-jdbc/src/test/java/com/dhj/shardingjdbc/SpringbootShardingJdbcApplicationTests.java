package com.dhj.shardingjdbc;

import com.dhj.shardingjdbc.entity.User;
import com.dhj.shardingjdbc.entity.UserOrder;
import com.dhj.shardingjdbc.mapper.UserMapper;
import com.dhj.shardingjdbc.mapper.UserOrderMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
class SpringbootShardingJdbcApplicationTests {

    @Autowired
    private UserMapper userMapper;


    /**
     * 测试使用年龄来进行分表分库
     */
    @Test
    void contextLoads() {
        for (int i = 0; i < 6; i++) {
            User user=User.builder()
                    .username("张三"+i)
                    .sex("男")
                    .age(i)
                    .birthday("2020-02-03").build();
            userMapper.addUser(user);
        }

    }

    /**
     * 测试使用年龄来进行分表分库
     */
    @Test
    void selectUser() {

        List<User> users = userMapper.selectUser();
        System.out.println(users);

    }
    /**
     * 测试使用年龄来进行分表分库
     */
    @Test
    void test1() {

            User user=User.builder()
                    .username("standardTest666")
                    .sex("男")
                    .age(22)
                    .birthday("2019-05-03").build();
            userMapper.addUser(user);
    }


    @Test
    void test2() {

        User user=User.builder()
                .username("SNOW")
                .sex("男")
                .age(27)
                .birthday("2021-10-03").build();
        userMapper.addUser(user);
        System.out.println(user.getUserid());
    }

    @Autowired
    private UserOrderMapper userOrderMapper;

    /**
     * 测试ShardingJDBC按年月分表
     */
    @Test
    void test3() {
        UserOrder userOrder = new UserOrder();
        userOrder.setUserid("1");
        userOrder.setCreatetime(new Date());
        userOrder.setOrdername("订单"+ Math.random()*16);
        userOrder.setYearmonth("202201");
        userOrderMapper.addUserOrder(userOrder);

        System.out.println(userOrder.getOrderid());
    }


}
