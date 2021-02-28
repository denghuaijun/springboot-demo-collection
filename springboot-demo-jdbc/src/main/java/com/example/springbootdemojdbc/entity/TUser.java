package com.example.springbootdemojdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//实体类对应数据库中的t_user表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TUser {
    private Long id;
    private String userName;
    private Integer sex;
    private Integer age;
    private Date birth;
}
