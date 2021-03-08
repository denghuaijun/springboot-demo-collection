package com.dhj.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义配置spring-security认证授权信息
 */
@ComponentScan
@EnableWebSecurity//开启spring安全认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private ISysUserService userService;

    /**
     * 添加认证信息
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //为安全框架添加认证用户并授权可以访问的权限名称，“/”代表都可以访问
        auth.inMemoryAuthentication().withUser("dhj").password("123456").authorities("showMember","delMember","updateMember","addMember");
        auth.inMemoryAuthentication().withUser("addUser").password("123456").authorities("addMember");

    }

    /**
     * 权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //拦截所有请求，只有通过表单认证的形式才能进入
       // http.authorizeRequests().mvcMatchers("/**").fullyAuthenticated().and().formLogin();//formLogin表单模式默认，也可以使用http弹窗模式
        //对某个地址的请求请求对应的权限名称
        http.authorizeRequests()
                .antMatchers("/showMember").hasAnyAuthority("showMember")
                .antMatchers("/addMember").hasAnyAuthority("addMember")
                .antMatchers("/delMember").hasAnyAuthority("delMember")
                .antMatchers("/updateMember").hasAnyAuthority("updateMember")
                //.antMatchers("/**").fullyAuthenticated().and().httpBasic()
                //自定义登陆页面放行/login请求
                .antMatchers("/login").permitAll()
                .antMatchers("/**").fullyAuthenticated().and().formLogin().loginPage("/login")
                // 解决不允许显示在iframe的问题,关闭防止csrf攻击，注销失败的可能存在的原因
                .and().csrf().disable();

    }
    /**
     * 密码编码器//java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        //自定义密码验证器
        return new MyPasswordEncoder();
    }
}
