package com.dhj.shardingjdbc.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * ShardingJDbc分库策略standard根据生日算法分布
 */
public class BirthdayAlgorithm implements PreciseShardingAlgorithm<String> {
    List<String> dateList = new ArrayList<>();
    //这个是用来分片数据源的所以集合的大小根据你的数据源而定

    {
        dateList.add("2021-01-01 00:00:00");
        dateList.add("2022-01-01 00:00:00");
    }

    /**
     *
     * @param collection 数据源集合  ds0  ds1
     * @param preciseShardingValue 分片对象，含有逻辑表名，字段name  字段value
     * @return 返回数据源
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String birthday = preciseShardingValue.getValue();
        Iterator<String> iterator = collection.iterator();
        String target=null;
        for (int i = 0; i < dateList.size(); i++) {
           target= iterator.next();
           if (birthday.compareTo(dateList.get(i))==-1){
               break;
           }
        }

        return target;
    }
}
