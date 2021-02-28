package com.dhj.demo.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 登录页面API
 */

@Controller
public class LoginController {

    @RequestMapping("/user/login")
    public  String login(
            @RequestParam("loginUsername")String userName,
            @RequestParam("loginPassword")String password,
            Model model, HttpSession session){
        System.out.println("登录操作。。。。。。。。。。。。。。start");
        //1、若用户名和密码正确，则登陆成功，反之则提示错误信息
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            model.addAttribute("msg","用户名/密码不能为空");
            return "login";
        }else if (!userName.equals("admin") || !"123456".equals(password)){
            model.addAttribute("msg","用户名/密码错误");
            return "login";
        }else {
            System.out.println("登录操作。。。。。。。。。。。。。。成功！");
            session.setAttribute("userSession",userName);
            //return "index";
            //通过视图跳转器及重定向
            return "redirect:/main.html";
        }
    }
}

