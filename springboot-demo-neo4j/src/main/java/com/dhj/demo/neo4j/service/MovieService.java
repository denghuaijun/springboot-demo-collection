package com.dhj.demo.neo4j.service;

import com.dhj.demo.neo4j.domain.Movie;
import com.dhj.demo.neo4j.domain.Person;
import com.dhj.demo.neo4j.dto.RelActedInDTO;

import java.util.List;
import java.util.Map;

public interface MovieService {
    /**
     * 获取所有电影信息
     * @return
     */
    List<Movie> findAll();

    /**
     * 根据电影标题获取电影信息
     * @param movieTitle
     * @return
     */
    Movie getMovie(String movieTitle);

    /**
     * 根据电影标题获取参演的人员
     * @param movieTitle
     * @return
     */
    List<Person> getActorsByMovieTitle(String movieTitle);

    /**
     * 根据电影标题获取对应电影参演人员
     * @param movieTitle
     * @return
     */
    List<Person> getActorsAndRolesByMovieTitle(String movieTitle);

    /**
     * 新增电影及参演人员及角色
     * @param relActedInDTO
     * @return
     */
    RelActedInDTO addActor(RelActedInDTO relActedInDTO);

    /**
     * 根据电影获取其中所有人员信息及对应关系
     * @param movieTitle
     * @return
     */
    Map<String,List<Person>> getPersonByMovieTitle(String movieTitle);
}
