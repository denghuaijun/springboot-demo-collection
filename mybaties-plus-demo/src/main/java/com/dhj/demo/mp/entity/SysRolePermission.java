package com.dhj.demo.mp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private Integer permId;


}
