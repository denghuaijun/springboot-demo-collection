package com.dhj.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dhj.demo.mp.entity.MpUser;
import com.dhj.demo.mp.entity.User;
import com.dhj.demo.mp.service.IMpUserService;
import com.dhj.demo.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;
import java.util.List;
import java.util.Objects;

/**
 * mybatis-plus测试类
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMpUserService mpUserService;

    /**
     * 测试查询
     */
    @Test
    public void selectList(){
        List<User> list = userService.list();
        System.out.println(list);
    }

    /**
     * 根据ID查询对象
     */
    @Test
    public void selectUserById(){
        MpUser byId = mpUserService.getById(1);
        System.out.println(byId.toString());
    }
    /**
     * 根据条件查询对象
     */
    @Test
    public void selectByCondition(){
        QueryWrapper<MpUser> wrapper = new QueryWrapper<MpUser>();
        //第一个condition字段是指条件是否生效
        wrapper.lambda().eq(false,MpUser::getName,"Tom");
        List<MpUser> list = mpUserService.list(wrapper);
        System.out.println(list.toString());
        //查询记录数
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        int count = userService.count(null);
        System.out.println("user表总记录数==："+count);
    }

    @Test
    public void selectByPage(){
        Page<MpUser> page = new Page<>(1,5);

        QueryWrapper<MpUser> queryWrapper = new QueryWrapper<>();
        Page<MpUser> mpUserPage = mpUserService.page(page);
        List<MpUser> records = mpUserPage.getRecords();
        System.out.println("总页数："+ mpUserPage.getPages());
    }

}
