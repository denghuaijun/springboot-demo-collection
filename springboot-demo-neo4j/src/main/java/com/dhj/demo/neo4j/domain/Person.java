package com.dhj.demo.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义人员节点及对应关系
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Person extends Neo4jEntity {
    //默认情况下，实体上的字段映射到节点的属性
    private String firstName;
    private String lastName;
    private Integer height;

    //对应演员指向电影的关系属性 对于电影，人员是外向关系  参演关系
    @JsonIgnoreProperties("models")
    @Relationship(type = "ACTED_IN",direction = Relationship.OUTGOING)
    private List<Movie> movieList;

    //导演关系
    @Relationship(type = "DIRECTED",direction = Relationship.OUTGOING)
    private List<Movie> dMovies;

    public Person(String firstName, String lastName, Integer height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
    }

    /**
     * 增加参演的电影
     * @param movie
     */
    public void addActMovie(Movie movie){
        if (CollectionUtils.isEmpty(this.movieList)){
            this.movieList= new ArrayList<>();
        }
        this.movieList.add(movie);
    }

    /**
     * 添加导演的电影
     * @param movie
     */
    public void addDirectMovie(Movie movie){
        if (CollectionUtils.isEmpty(this.dMovies)){
            this.dMovies= new ArrayList<>();
        }
        this.dMovies.add(movie);
    }
}
