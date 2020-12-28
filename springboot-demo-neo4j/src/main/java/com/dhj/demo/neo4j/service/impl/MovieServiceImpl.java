package com.dhj.demo.neo4j.service.impl;

import com.dhj.demo.neo4j.domain.*;
import com.dhj.demo.neo4j.dto.RelActedInDTO;
import com.dhj.demo.neo4j.repository.MovieRepository;
import com.dhj.demo.neo4j.repository.PersonRepository;
import com.dhj.demo.neo4j.service.MovieService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Movie> findAll() {
        return Lists.newArrayList(movieRepository.findAll());
    }

    @Override
    public Movie getMovie(String movieTitle) {
        return movieRepository.findByTitle(movieTitle);
    }

    @Override
    public List<Person> getActorsByMovieTitle(String movieTitle) {
        Page<Person> personPage= movieRepository.getActorsInMovieFromTitle(movieTitle, PageRequest.of(1, 10));
        return personPage.toList();
    }

    @Override
    public List<Person> getActorsAndRolesByMovieTitle(String movieTitle) {
        List<Person> people = new ArrayList<>();
        List<RelActedIn> relActedInList = movieRepository.getActorsAndRolesThatActInMovieFromTitle(movieTitle);
        relActedInList.forEach(relActedIn -> people.add(relActedIn.getPerson()));
        return people;
    }

    @Override
    public RelActedInDTO addActor(RelActedInDTO relActedInDTO) {
        Movie movie = relActedInDTO.getMovie();
        Person person = relActedInDTO.getPerson();
        RelActedIn relActedIn = relActedInDTO.getRelActedIn();
        //根据入参判断库中是否已存在该电影信息
        Movie mm = movieRepository.findByTitle(movie.getTitle());
        if (mm ==null){
            mm=movie;
            mm.setRelActedIns(new ArrayList<>(1));
        }
        //同样根据入参人员姓名判断是否存在该人
        Person pp = personRepository.findByName(person.getName());
        if (pp == null){
            pp = person;
            pp.setRelActedIns(new ArrayList<>(1));
        }
        RelActedIn actedIn=null;
        //判断此人是否参演
        for (RelActedIn in:mm.getRelActedIns()){
            if (StringUtils.equals(pp.getName(),in.getPerson().getName())){
                actedIn=in;
                break;
            }
        }
        if (actedIn ==null){
            //新建关系
            actedIn = relActedIn;
            actedIn.setMovie(mm);
            actedIn.setPerson(pp);
            mm.getRelActedIns().add(actedIn);
            pp.getRelActedIns().add(actedIn);
        }else {
            //若已经存在此人且参演，则进行修改参演关系角色
            actedIn.setRoles(relActedIn.getRoles());
        }
        movieRepository.save(mm);
        personRepository.save(pp);
        //组织返回数据
        RelActedInDTO dto = new RelActedInDTO();
        dto.setMovie(movie);
        dto.setPerson(person);
        dto.setRelActedIn(actedIn);

        return dto;
    }

    @Override
    public Map<String, List<Person>> getPersonByMovieTitle(String movieTitle) {

        Movie movie = movieRepository.findByTitle(movieTitle);
        Map<String, List<Person>> retMap = new HashMap<String, List<Person>>();
        // 取得参演人员
        List<Person> list = new ArrayList<Person>();
        for (RelActedIn relActed : movie.getRelActedIns()) {
            list.add(relActed.getPerson());
        }
        retMap.put("actedIns", list);
        // 取得导演人员
        list = new ArrayList<Person>();
        for (RelDirected relDirected : movie.getRelDirecteds()) {
            list.add(relDirected.getPerson());
        }
        retMap.put("directeds", list);
        // 取得评介人员
        list = new ArrayList<Person>();
        for (RelReviewed relReviewed : movie.getRelRevieweds()) {
            list.add(relReviewed.getPerson());
        }
        retMap.put("revieweds", list);
        return retMap;

    }
}
