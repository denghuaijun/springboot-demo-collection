package com.dhj.springboot.config;

import com.dhj.demo.mp.entity.SysPermission;
import com.dhj.demo.mp.service.ISysPermissionService;
import com.dhj.springboot.jwtfilter.JWTAuthorizationFilter;
import com.dhj.springboot.jwtfilter.JwtLoginFilter;
import com.dhj.springboot.service.SysUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * 自定义配置spring-security认证授权信息
 */
@ComponentScan
@EnableWebSecurity//开启spring安全认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ISysPermissionService permissionService;
    @Autowired
    private SysUserDetailService userDetailService;

    /**
     * 添加认证信息
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //为安全框架添加认证用户并授权可以访问的权限名称，“/”代表都可以访问
        //第一种方式写死的直接循环
//        auth.inMemoryAuthentication().withUser("dhj").password("123456").authorities("showMember","delMember","updateMember","addMember");
//        auth.inMemoryAuthentication().withUser("addUser").password("123456").authorities("addMember");
        //第二种方式，推荐，借用userDetailsService接口
        auth.userDetailsService(userDetailService).passwordEncoder(new MyPasswordEncoder());

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
       /* http.authorizeRequests()
                .antMatchers("/showMember").hasAnyAuthority("showMember")
                .antMatchers("/addMember").hasAnyAuthority("addMember")
                .antMatchers("/delMember").hasAnyAuthority("delMember")
                .antMatchers("/updateMember").hasAnyAuthority("updateMember") 修改为查询数据库*/
        List<SysPermission> permissionList = permissionService.list();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.authorizeRequests();
        permissionList.forEach(sysPermission -> {
            authorizeRequests.antMatchers(sysPermission.getUrl()).hasAnyAuthority(sysPermission.getPermTag());
        });
        //.antMatchers("/**").fullyAuthenticated().and().httpBasic()
                //自定义登陆页面放行/login请求
        /*authorizeRequests.antMatchers("/login").permitAll()
                .antMatchers("/**").fullyAuthenticated().and().formLogin().loginPage("/login")
                // 解决不允许显示在iframe的问题,关闭防止csrf攻击，注销失败的可能存在的原因
                .and().csrf().disable();*/
        //整合jwt
        authorizeRequests.antMatchers("/auth/login").permitAll()
                .antMatchers("/**").fullyAuthenticated().and().formLogin().loginPage("/login")
                //添加登陆和请求授权过滤器
                .and().addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager())).csrf().disable()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }
    /**
     * 密码编码器//java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
     * There is no PasswordEncoder mapped for the id "null"
     * 原因:升级为Security5.0以上密码支持多中加密方式，恢复以前模式
     *
     * @return
     */
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
