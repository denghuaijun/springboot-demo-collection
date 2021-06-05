package com.dhj.shardingjdbc.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 根据年月对ds1进行分表
 */
public class YearMonthAlgorithm implements PreciseShardingAlgorithm<String> {
   private static final String SPLITtER="_";

    /**
     *
     * @param collection 数据源集合  ds0  ds1
     * @param preciseShardingValue 分片对象，含有逻辑表名，字段name  字段value
     * @return 返回数据源
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String tableName=preciseShardingValue.getLogicTableName()+"_"+preciseShardingValue.getValue();
        System.out.println("分片键的值为："+preciseShardingValue.getValue()+"----tableName:"+tableName);
        return tableName;
    }
}
