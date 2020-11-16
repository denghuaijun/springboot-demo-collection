package com.dhj.demo.business.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExcelVersion {

    /**
     *
     */
    XLS("2003", ".xls"),
    /**
     *
     */
    XLSX("2007", ".xlsx");


    private String version;
    private String fileSuffix;
}
