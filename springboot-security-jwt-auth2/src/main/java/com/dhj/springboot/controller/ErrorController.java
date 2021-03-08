package com.dhj.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常控制类
 * new ErrorPage(HttpStatus.BAD_REQUEST,"/error/400"),
 *                 new ErrorPage(HttpStatus.UNAUTHORIZED,"/error/401"),
 *                 new ErrorPage(HttpStatus.FORBIDDEN,"/error/403"),
 *                 new ErrorPage(HttpStatus.NOT_FOUND,"/error/404"),
 *                 new ErrorPage(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"/error/415"),
 *                 new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/500")
 */
@RestController
public class ErrorController {

    @RequestMapping("/error/401")
    public String error_401(){
        return HttpStatus.UNAUTHORIZED.getReasonPhrase();
    }
    @RequestMapping("/error/400")
    public String error_400(){
        return HttpStatus.BAD_REQUEST.getReasonPhrase();
    }
    @RequestMapping("/error/403")
    public String error_403(){
        return HttpStatus.FORBIDDEN.getReasonPhrase();
    }
    @RequestMapping("/error/404")
    public String error_404(){
        return HttpStatus.NOT_FOUND.getReasonPhrase();
    }
    @RequestMapping("/error/415")
    public String error_415(){
        return HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase();
    }
    @RequestMapping("/error/500")
    public String error_500(){
        return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }
}
