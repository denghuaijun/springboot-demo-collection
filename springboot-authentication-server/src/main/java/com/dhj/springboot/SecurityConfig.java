/*
package com.dhj.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

*/
/**
 * 自定义配置spring-security认证授权信息
 *//*

//@Component
//@EnableWebSecurity//开启spring安全认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    */
/**
     * 添加认证信息
     * @param auth
     * @throws Exception
     *//*

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //为安全框架添加认证用户并授权可以访问的权限名称，“/”代表都可以访问
        auth.
                inMemoryAuthentication()
                .withUser("dhj")
                .password(passwordEncoder().encode("123456"))
                .authorities("/*");

    }

    */
/**
     * 权限控制
     * @param http
     * @throws Exception
     *//*

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated() //所有请求都需要通过认证
                .and()
                .httpBasic() //Basic登录
                .and()
                .csrf().disable(); //关跨域保护

    }
    */
/**
     * 密码编码器//java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
     * There is no PasswordEncoder mapped for the id "null"
     * 原因:升级为Security5.0以上密码支持多中加密方式，恢复以前模式
     *
     * @return
     *//*

//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
*/
