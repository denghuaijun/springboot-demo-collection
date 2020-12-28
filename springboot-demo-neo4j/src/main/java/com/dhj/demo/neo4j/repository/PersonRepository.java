package com.dhj.demo.neo4j.repository;

import com.dhj.demo.neo4j.domain.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PersonRepository extends Neo4jRepository<Person,Long>,CustomizedRepository<Person> {

    Person findByfirstName(@Param("firstName")String firstName);
    Collection<Person> findByfirstNameLike(@Param("firstName")String firstName);
}
