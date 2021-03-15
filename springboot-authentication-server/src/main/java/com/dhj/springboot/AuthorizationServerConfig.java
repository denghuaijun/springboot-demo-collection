/*
package com.dhj.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

*/
/**
 * 作为auth2的认证服务中心
 * 启动服务会默认加载auth2相关认证授权地址
 * requestMatchers=[Ant [pattern='/oauth/token'], Ant [pattern='/oauth/token_key'], Ant [pattern='/oauth/check_token']]]
 *
 * //http://localhost:10011/oauth/authorize?client_id=test_app_id&response_type=code 获取授权码
*//*

//@Component
//@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    */
/**
     * 允许表单提交，同时需要开启检查accessToken是否有效，默认拒绝
     * @param security
     * @throws Exception
     *//*

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单提交
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端appID
                .withClient("test")
                //类似于appKey
                .secret(passwordEncoder.encode("test_secret"))
                //设置oauth2的认证授权模式，为授权码
                .authorizedGrantTypes("authorization_code")
                //授权哪些请求
                .scopes("all")
                // 资源的id
                .resourceIds("test_resource")
                // 回调地址
                .redirectUris("http://localhost:10011/callback");

    }
}
*/
