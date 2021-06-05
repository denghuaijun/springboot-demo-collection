package com.dhj.shardingjdbc.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserOrder {
    private String orderid;
    private String userid;
    private Date createtime;
    private String ordername;
    private String yearmonth;
}
