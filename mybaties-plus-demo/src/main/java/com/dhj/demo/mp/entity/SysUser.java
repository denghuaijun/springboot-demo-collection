package com.dhj.demo.mp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author 邓怀俊
 * @since 2021-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String realname;

    private String password;

    @TableField("createDate")
    private LocalDate createDate;

    @TableField("lastLoginTime")
    private LocalDate lastLoginTime;

    private Integer enabled;

    @TableField("accountNonExpired")
    private Integer accountNonExpired;

    @TableField("accountNonLocked")
    private Integer accountNonLocked;

    @TableField("credentialsNonExpired")
    private Integer credentialsNonExpired;


}
