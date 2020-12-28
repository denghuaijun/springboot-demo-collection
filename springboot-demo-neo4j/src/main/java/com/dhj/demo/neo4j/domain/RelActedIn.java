package com.dhj.demo.neo4j.domain;

import com.dhj.demo.neo4j.constants.Const;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.List;

/**
 * 演员参演关系实体
 *  分别使用@StartNode和@EndNode注解表示关系的开始节点和结束节点
 *   为了避免Json序列化时候的无限循环，使用@JsonIgnore注解取消对开始节点和结束节点的序列化，
 *   并在toString中不做开始节点和结束节点的toString
 */
@Data
@RelationshipEntity(type = Const.REL_TYPE_ACTEDIN)
public class RelActedIn extends Neo4jEntity {

    @Property
    private List<String> roles;

    @StartNode
    @JsonIgnore
    private Person person;

    @EndNode
    @JsonIgnore
    private Movie movie;
}
