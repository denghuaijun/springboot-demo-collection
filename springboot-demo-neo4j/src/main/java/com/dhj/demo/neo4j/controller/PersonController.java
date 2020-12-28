package com.dhj.demo.neo4j.controller;

import com.dhj.demo.neo4j.domain.Movie;
import com.dhj.demo.neo4j.domain.Person;
import com.dhj.demo.neo4j.repository.MovieRepository;
import com.dhj.demo.neo4j.repository.PersonRepository;
import com.dhj.demo.neo4j.service.PersonService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/findByfirstName")
    public Person findByfirstName(@RequestParam String name){
        return personService.findByfirstName(name);
    }
    @GetMapping("/findByfirstNameLike")
    public List<Person> findByfirstNameLike(@RequestParam String name){
        return Lists.newArrayList(personService.findByNameLike(name));
    }
    @GetMapping("/findById")
    public List<Person> findById(@RequestParam Integer id){
        Iterable<Person> byNodeId = personRepository.findByNodeId(Person.class, id);
        return Lists.newArrayList(byNodeId);
    }
    @GetMapping("/all")
    public List<Person> findAll(){
        return Lists.newArrayList(personRepository.findAll());
    }

    /**
     * 初始化图数据
     * @return
     */
    @GetMapping("/init")
    public List<Person> init(){
        //先清除原有数据
        Iterable<Person> iterable = personRepository.findAll();
        for (Person person:iterable){
            person.setMovieList(null);
            person.setDMovies(null);
            personRepository.save(person);
        }
        personRepository.deleteAll();
        movieRepository.deleteAll();
        //以下数据纯属虚构
        //参演了 《建国大业A》
        Person clPerson  = new Person("ChengLongP", "Jack", 175);
        //参演了《长城B》
        Person jtPerson = new Person("JingTianP", "JT", 170);
        //参演了《长城B》 《建国大业A》
        Person ldhPerson = new Person("LiuDeHuaP", "DeHua", 180);
        //导演了《长城B》
        Person zymPerson  = new Person("ZhangYiMouP", "YiMou", 176);

//        personRepository.save(clPerson);
//        personRepository.save(jtPerson);
//        personRepository.save(ldhPerson);
//        personRepository.save(zymPerson);

        Movie jgMovie = new Movie("建国大业A" , "history", 9000);
        // movieRepository.save(jgMovie);
        Movie clMovie = new Movie("长城B", "science fiction", 5000);
        //movieRepository.save(clMovie);

        clPerson.addActMovie(jgMovie);

        jtPerson.addActMovie(clMovie);

        ldhPerson.addActMovie(jgMovie);
        ldhPerson.addActMovie(clMovie);

        zymPerson.addDirectMovie(clMovie);

        personRepository.save(clPerson);
        personRepository.save(jtPerson);
        personRepository.save(ldhPerson);
        personRepository.save(zymPerson);

        return Lists.newArrayList(personRepository.findAll());
    }
}
