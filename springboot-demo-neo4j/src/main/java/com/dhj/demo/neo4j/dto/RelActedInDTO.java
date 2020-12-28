package com.dhj.demo.neo4j.dto;

import com.dhj.demo.neo4j.domain.Movie;
import com.dhj.demo.neo4j.domain.Person;
import com.dhj.demo.neo4j.domain.RelActedIn;
import lombok.Data;

import java.io.Serializable;

/**
 * RelActedInDTO.java为业务DTO根据输入输出设计自己定义
 */
@Data
public class RelActedInDTO implements Serializable {
    private static final long serialVersionUID= 7886616463887142217L;
    private RelActedIn relActedIn;
    private Movie movie;
    private Person person;
}
