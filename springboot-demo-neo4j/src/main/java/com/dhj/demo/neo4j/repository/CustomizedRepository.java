package com.dhj.demo.neo4j.repository;

import java.util.List;

/**
 * 自定义图仓库接口类
 * @param <T>
 */
public interface CustomizedRepository<T> {
    List<T> someCustomMethod();
    Iterable<T> findByNodeId(Class<T> objectType,Integer id);
}
