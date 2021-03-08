package com.dhj.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class indexController {

    @RequestMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }
    @GetMapping(value ="/showMember")
    @ResponseBody
    public String show(){
        return "showMember";
    }
    @GetMapping(value ="/addMember")
    @ResponseBody
    public String addMember(){
        return "addMember";
    }
    @GetMapping(value ="/updateMember")
    @ResponseBody
    public String updateMember(){
        return "updateMember";
    }
    @GetMapping(value ="/delMember")
    @ResponseBody
    public String delMember(){
        return "delMember";
    }
}
