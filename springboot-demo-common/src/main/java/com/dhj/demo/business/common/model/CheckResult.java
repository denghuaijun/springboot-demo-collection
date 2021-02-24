package com.dhj.demo.business.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckResult implements Serializable {

    private static final long serialVersionUID= -2796717234722308384L;

    private String name;
    private String idCard;
    private String beginDate;
    private String result;
}
