package com.dhj.demo.neo4j.domain;

import com.dhj.demo.neo4j.constants.Const;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

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
    /**
     * 电影标语
     */
    private String tagline;

    /**
     * 版本
     */
    private Integer released;

    public Movie(String title, String category, Integer revenue) {
        this.title = title;
        this.category = category;
        this.revenue = revenue;
    }

    @Relationship(type = Const.REL_TYPE_ACTEDIN,direction =Relationship.INCOMING )
    private List<RelActedIn> relActedIns;

    @Relationship(type = Const.REL_TYPE_DIRECTED,direction = Relationship.INCOMING)
    private List<RelDirected> relDirecteds;

    @Relationship(type = Const.REL_TYPE_REVIEWED,direction = Relationship.INCOMING)
    private List<RelReviewed> relRevieweds;
}
