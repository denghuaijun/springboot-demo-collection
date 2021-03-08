package com.dhj.demo.mp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("roleName")
    private String roleName;

    @TableField("roleDesc")
    private String roleDesc;


}
