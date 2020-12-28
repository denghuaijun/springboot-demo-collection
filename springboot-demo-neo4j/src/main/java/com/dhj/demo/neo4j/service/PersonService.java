package com.dhj.demo.neo4j.service;

import com.dhj.demo.neo4j.domain.Person;

import java.util.Collection;

public interface PersonService {

    public Person findByfirstName(String firstName);

    public Collection<Person> findByNameLike(String firstName);
}
