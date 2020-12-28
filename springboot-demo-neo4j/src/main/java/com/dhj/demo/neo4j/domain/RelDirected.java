package com.dhj.demo.neo4j.domain;

import com.dhj.demo.neo4j.constants.Const;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 定义导演关系
 */
@Data
@RelationshipEntity(type = Const.REL_TYPE_DIRECTED)
public class RelDirected extends Neo4jEntity {
    @StartNode
    @JsonIgnore
    private Person person;
    @EndNode
    @JsonIgnore
    private Movie movie;
}
