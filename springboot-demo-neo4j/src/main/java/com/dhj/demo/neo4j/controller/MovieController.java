package com.dhj.demo.neo4j.controller;

import com.dhj.demo.business.common.response.ResultDTO;
import com.dhj.demo.business.common.response.ResultDTOTool;
import com.dhj.demo.neo4j.domain.Movie;
import com.dhj.demo.neo4j.domain.Person;
import com.dhj.demo.neo4j.dto.RelActedInDTO;
import com.dhj.demo.neo4j.repository.MovieRepository;
import com.dhj.demo.neo4j.service.MovieService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

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
    /**
     * 通过电影名取得电影
     *
     * @return RestResponse
     */
    @RequestMapping(value = "/getMovieByTitle")
    public ResultDTO getMovieByTitle(@RequestBody Movie movie) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setData(movieService.getMovie(movie.getTitle()));
        return ResultDTOTool.setSuccess(resultDTO);
    }

    /**
     * 通过电影名取得演员
     *
     * @return RestResponse
     */
    @RequestMapping(value = "/getActorsByMovieTitle")
    public ResultDTO getActorsByMovieTitle(@RequestBody Movie movie) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setData(movieService.getActorsByMovieTitle(movie.getTitle()));
        return ResultDTOTool.setSuccess(resultDTO);
    }

    /**
     * 通过电影名取得演员(包含参演角色)
     *
     * @return RestResponse
     */
    @RequestMapping(value = "/getActorsAndRolesByMovieTitle")
    public ResultDTO getActorsAndRolesByMovieTitle(@RequestBody Movie movie) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setData(movieService.getActorsAndRolesByMovieTitle(movie.getTitle()));
        return ResultDTOTool.setSuccess(resultDTO);
    }

    /**
     * 通过电影名取得相关人员和关系
     *
     * @return RestResponse
     */
    @RequestMapping(value = "/getPersonsByMovieTitle")
    public ResultDTO getPersonsByMovieTitle(@RequestBody Movie movie) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setData(movieService.getPersonByMovieTitle(movie.getTitle()));
        return ResultDTOTool.setSuccess(resultDTO);
    }

    /**
     * 为电影添加演员
     *
     * @return RestResponse
     */
    @RequestMapping(value = "/addActors")
    public ResultDTO addActors(@RequestBody RelActedInDTO relActedInDTO) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setData(movieService.addActor(relActedInDTO));
        return ResultDTOTool.setSuccess(resultDTO);
    }

}
