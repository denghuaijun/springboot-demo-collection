package com.dhj.demo.neo4j.repository;

import com.dhj.demo.neo4j.domain.Movie;
import com.dhj.demo.neo4j.domain.Person;

import java.util.List;

public interface CustomizedMovieRepository  {
    List<Movie> someCustomMethod();
    Iterable<Movie> findUseClassMethod(Integer id);
    Iterable<Person> findPersonMethod(String name);
}
