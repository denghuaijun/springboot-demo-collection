package com.dhj.demo.neo4j;

import com.dhj.demo.neo4j.domain.Movie;
import com.dhj.demo.neo4j.repository.MovieRepository;
import com.dhj.demo.neo4j.repository.PersonRepository;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testNeo4j(){
        Iterable<Movie> all = movieRepository.findAll();
        Lists.newArrayList(all).forEach(movie -> System.out.println(movie.getTitle()));
    }
}
