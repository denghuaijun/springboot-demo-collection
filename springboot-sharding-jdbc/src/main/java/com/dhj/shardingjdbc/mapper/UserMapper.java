package com.dhj.shardingjdbc.mapper;

import com.dhj.shardingjdbc.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserMapper {

    @Insert("insert into user(username,age,sex,birthday) values(#{username},#{age},#{sex},#{birthday})")
    @Options(useGeneratedKeys = true,keyColumn = "userid",keyProperty = "userid")
     void addUser(User user);

    @Select("select * from user")
     List<User> selectUser();
}
