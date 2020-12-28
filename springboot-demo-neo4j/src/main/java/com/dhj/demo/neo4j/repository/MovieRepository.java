package com.dhj.demo.neo4j.repository;

import com.dhj.demo.neo4j.domain.Movie;
import com.dhj.demo.neo4j.domain.Person;
import com.dhj.demo.neo4j.domain.RelActedIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MovieRepository extends Neo4jRepository<Movie,Long>,CustomizedMovieRepository {
    //@Depth(value = 2)查询的关系深度
    Movie findByTitle(@Param("title")String title);
    Collection<Movie> findByRevenueGreaterThan(@Param("revenue") int revenue);
    @Query("MATCH (n) where id(n)=$0 return n")
    Movie getMovieById(Integer id);
    @Query("MATCH (m:Movie{title:$0}) return m")
    Movie getMovieFormTitle(String movieTitle);

    /**
     * 根据电影标题获取对应此电影的参演演员列表，并按照演员名称进行分页，排序
     * @param movieTitle
     * @param page
     * @return
     */
    @Query(value = "MATCH (m:movie{title:$0})<-[:ACTED_IN]-(p) return p order by p.name"
            ,countQuery = "MATCH (m:movie{title:$0})<-[:ACTED_IN]-(p) return COUNT(P) ")
    Page<Person> getActorsInMovieFromTitle(String movieTitle, PageRequest page);

    /**
     * 获取演员在对应电影名为${movieTitle}电影中的扮演的角色
     * @param movieTitle
     * @return
     */
    @Query("MATCH p=(movie:Movie {title:$0})<-[relActedIn:ACTED_IN]-(actor) RETURN p")
    List<RelActedIn> getActorsAndRolesThatActInMovieFromTitle(String movieTitle);

    // returns users who directed a movie
    @Query("MATCH (movie:Movie {title:$0})<-[DIRECTED]-(user) RETURN user")
    List<Person> getUsersWhoRatedMovieFromTitle(String movieTitle);

}
