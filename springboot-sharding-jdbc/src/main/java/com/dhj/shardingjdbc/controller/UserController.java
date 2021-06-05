package com.dhj.shardingjdbc.controller;

import com.dhj.shardingjdbc.entity.User;
import com.dhj.shardingjdbc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;



    @RequestMapping("/addUser")
    public String add(){
        User user =User.builder()
                .username("张三")
                .age(20)
                .birthday("2020-02-3")
                .sex("男").build();
        userMapper.addUser(user);
        return "success";
    }

    @RequestMapping("/selectUser")
    public String queryUser(){
        return userMapper.selectUser().toString();
    }
}
