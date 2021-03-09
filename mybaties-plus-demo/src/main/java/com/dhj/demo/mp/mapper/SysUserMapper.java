package com.dhj.demo.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dhj.demo.mp.entity.SysPermission;
import com.dhj.demo.mp.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 邓怀俊
 * @since 2021-03-08
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询用户信息，这里使用注解@Select也可以
     * @param userName
     * @return
     */
    SysUser selectUserByName(@Param("userName") String userName);

    /**
     * 根据用户名查询对应的权限列表
     * @param userName
     * @return
     */
    List<SysPermission> selectPermissionListByUserName(@Param("userName") String userName);
}
