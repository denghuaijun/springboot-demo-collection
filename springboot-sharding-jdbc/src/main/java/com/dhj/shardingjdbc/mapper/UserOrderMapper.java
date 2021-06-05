package com.dhj.shardingjdbc.mapper;

import com.dhj.shardingjdbc.entity.UserOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderMapper {

    @Insert("insert into user_order(userid,ordername,createtime,yearmonth) values(#{userid},#{ordername},#{createtime},#{yearmonth})")
    @Options(useGeneratedKeys = true,keyColumn = "orderid",keyProperty = "orderid")
    void addUserOrder(UserOrder userOrder);
}
