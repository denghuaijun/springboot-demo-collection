package com.dhj.springboot.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * springboot自定义异常
 */
@Configuration
public class WebServerAutoConfiguration {

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(){
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        List<ErrorPage> errorPageList = Arrays.asList(
                new ErrorPage(HttpStatus.BAD_REQUEST,"/error/400"),
                new ErrorPage(HttpStatus.UNAUTHORIZED,"/error/401"),
                new ErrorPage(HttpStatus.FORBIDDEN,"/error/403"),
                new ErrorPage(HttpStatus.NOT_FOUND,"/error/404"),
                new ErrorPage(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"/error/415"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/500")

        );
         factory.addErrorPages(errorPageList.toArray(ErrorPage[]::new));
        return factory;
    }
}
