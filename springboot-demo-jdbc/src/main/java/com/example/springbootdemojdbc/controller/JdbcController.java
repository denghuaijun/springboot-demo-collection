package com.example.springbootdemojdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 测试原生的JDBC template操作CRUD
 */
@RestController
public class JdbcController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/selectUserList")
    public String queryUserList(){
        String sql="select * from t_user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps.toString();
    }
    @RequestMapping("/addUser")
    public String addUser(){
        String sql="insert into t_user values(null,'tom',0,28,now())";
        jdbcTemplate.update(sql);
        return "add success";
    }
    @RequestMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id")Long id){
        String sql= "update t_user set user_name=?,age=? where id="+id;
        Object[] objects = new Object[2];
        objects[0]="luccy";
        objects[1]="66";
        jdbcTemplate.update(sql,objects);
        return "update success";

    }
    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id")Long id){
        String sql= "delete from t_user where id="+id;
        jdbcTemplate.update(sql);
        return "del success";

    }


}
