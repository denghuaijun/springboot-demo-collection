package com.dhj.demo.neo4j.service.impl;

import com.dhj.demo.neo4j.domain.Person;
import com.dhj.demo.neo4j.repository.PersonRepository;
import com.dhj.demo.neo4j.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findByfirstName(String firstName) {
        return personRepository.findByfirstName(firstName);
    }

    @Override
    public Collection<Person> findByNameLike(String firstName) {
        return personRepository.findByfirstNameLike(firstName);
    }
}
