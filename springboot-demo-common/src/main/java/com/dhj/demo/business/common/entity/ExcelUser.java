package com.dhj.demo.business.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 邓怀俊
 * @since 2020-11-10
 */
@Data
@AllArgsConstructor
public class ExcelUser implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private String age;

    /**
     * 电子邮箱
     */
    private String email;




}
