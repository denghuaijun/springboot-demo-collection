package com.dhj.demo.neo4j.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 定义电影节点，电影作为结束节点
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@NodeEntity
public class Movie extends Neo4jEntity{
    /**
     * 电影标题
     */
    private String title;
    /**
     * 电影类别
     */
    private String category;
    /**
     * 收入
     */
    private Integer revenue;
}
