package com.dhj.springboot.service;

import com.dhj.demo.mp.entity.SysPermission;
import com.dhj.demo.mp.entity.SysUser;
import com.dhj.demo.mp.mapper.SysUserMapper;
import com.dhj.springboot.bean.SysUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SysUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * security默认登陆回调函数，登陆的时候就会执行这个方法
     * @param userName 登陆用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //1、根据用户名查询是否存在这个用户
        SysUser sysUser = sysUserMapper.selectUserByName(userName);
        if (sysUser == null){
            return null;
        }
        SysUserDetails userDetails = new SysUserDetails();
        BeanUtils.copyProperties(sysUser,userDetails);
        //2、查询出用户对应的权限列表
        List<SysPermission> permissionList = sysUserMapper.selectPermissionListByUserName(userName);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        permissionList.forEach(sysPermission -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(sysPermission.getPermTag()));
        });
        //3、将查询的权限添加到security框架中
        userDetails.setGrantedAuthorityList(grantedAuthorities);
        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setEnabled(true);
        userDetails.setCredentialsNonExpired(true);


        return userDetails;
    }
}
