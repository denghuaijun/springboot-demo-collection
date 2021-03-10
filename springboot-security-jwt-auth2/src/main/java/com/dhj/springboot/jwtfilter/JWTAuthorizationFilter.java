package com.dhj.springboot.jwtfilter;

import com.dhj.springboot.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 外部请求API授权
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    //从请求头中的header页面获取权限列表
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("token");
        String userName = JwtUtils.getUserName(token);
        if (StringUtils.isNotEmpty(userName)){
            //获取用户对应的权限
            List<SimpleGrantedAuthority> authoris = JwtUtils.getAuthoris(token);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userName,null,authoris));
        }
        super.doFilterInternal(request, response, chain);
    }
}
