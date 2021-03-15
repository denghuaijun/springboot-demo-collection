package com.dhj.springboot.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.stereotype.Component;

/*
当前项目为认证授权中心
 */
@Component
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单提交 检查accessToken是否有效期的情况下
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }

    /**
     * 分配我们的appid和appkey clentid clientkey
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                // appid
//                .withClient("mayikt")
//                // appsecret
//                .secret(passwordEncoder.encode("mayikt_secret"))
//                // 授权码
//                .authorizedGrantTypes("authorization_code")
//                // 作用域
//                .scopes("all")
//                // 资源的id
//                .resourceIds("mayikt_resource")
//                // 回调地址
//                .redirectUris("http://www.mayikt.com/callback");

        // 读数据库
        clients.inMemory()
                // appid
                .withClient("mayikt_appid")
                .secret(passwordEncoder.encode("mayikt_pwd"))
                // 授权码
                .authorizedGrantTypes("authorization_code")
                // 作用域 表示所有的接口都可以访问 分配我们的appid 调用接口的权限
                .scopes("all")
                .resourceIds("mayikt_resource")
                // 用户选择授权之后，跳转到该地址传递code授权码
                .redirectUris("http://www.mayikt.com/callback");


    }
}