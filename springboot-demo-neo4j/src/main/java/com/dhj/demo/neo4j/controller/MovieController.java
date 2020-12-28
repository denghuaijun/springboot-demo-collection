package com.dhj.demo.neo4j.controller;

import com.dhj.demo.neo4j.domain.Movie;
import com.dhj.demo.neo4j.domain.Person;
import com.dhj.demo.neo4j.repository.MovieRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * 查询所有电影信息
     * @return
     */
    @GetMapping("/all")
    public List<Movie> findAll(){
        Iterable<Movie> all = movieRepository.findAll();
        return Lists.newArrayList(all);
    }
    @GetMapping("/findByRevenueGt")
    public List<Movie> findByRevenueGt(@RequestParam Integer revenue){
        return Lists.newArrayList(movieRepository.findByRevenueGreaterThan(revenue));
    }

    @GetMapping("/customized")
    public List<Movie> findCustomzied(){
        return movieRepository.someCustomMethod();
    }

    @GetMapping("/customFindById")
    public List<Movie> findCustomById(@RequestParam Integer id){
        return Lists.newArrayList(movieRepository.findUseClassMethod(id));
    }
    @GetMapping("/customizedFindPersonByName")
    public List<Person> findByPersion(@RequestParam String name){
        return Lists.newArrayList(movieRepository.findPersonMethod(name));
    }
}
