package com.example.springbootdemojdbc.mapper;

import com.example.springbootdemojdbc.entity.TUser;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface TUserMapper {

    List<TUser> selectUserList();

}
