package com.example.springbootdemojdbc.controller;

import com.example.springbootdemojdbc.entity.TUser;
import com.example.springbootdemojdbc.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MybatisController {

    @Autowired
    private TUserMapper userMapper;
    @RequestMapping("/getUserList")
    public List<TUser> queryList(){
        List<TUser> tUsers = userMapper.selectUserList();
        return tUsers;
    }
}
