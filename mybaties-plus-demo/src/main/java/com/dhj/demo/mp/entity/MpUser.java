package com.dhj.demo.mp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 邓怀俊
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MpUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 技能
     */
    private String skill;

    /**
     * 评价
     */
    private String evaluate;

    /**
     * 分数
     */
    private Long fraction;


}
