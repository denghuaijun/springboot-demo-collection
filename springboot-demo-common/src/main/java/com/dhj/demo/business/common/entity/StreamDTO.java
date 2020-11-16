package com.dhj.demo.business.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by libin on 2018/3/29.
 */

@Data
public class StreamDTO implements Serializable {

    private static final long serialVersionUID = 3775036925728724449L;
    private byte[] b;

    private String fileName;

}
