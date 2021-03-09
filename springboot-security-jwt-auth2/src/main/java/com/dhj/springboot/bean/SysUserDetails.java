package com.dhj.springboot.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 登陆用户实体转换bean
 */
@Data
public class SysUserDetails implements UserDetails {
    private Integer id;
    private String username;
    private String realname;
    private String password;
    private LocalDate createDate;
    private LocalDate lastLoginTime;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    //用户所有权限的集合
    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }


}
