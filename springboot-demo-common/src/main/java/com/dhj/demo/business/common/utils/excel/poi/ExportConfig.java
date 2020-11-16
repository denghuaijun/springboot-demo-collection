package com.dhj.demo.business.common.utils.excel.poi;

import com.dhj.demo.business.common.enums.ExcelVersion;
import com.dhj.demo.business.common.utils.DateUtil;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * POIexcel导出配置类
 */
@Getter
public class ExportConfig {
    /**
     * 字段
     */
    private List<Column> column =new ArrayList<>();

    /**
     * 时间类型格式
     */
    private String dataFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * sheet 名称
     */
    private String title = "Sheet";

    /**
     * fileName 文件名 默认为当前系统时间
     */
    private String fileName= DateUtil.getNowDateTime();

    private ExcelVersion version = ExcelVersion.XLSX;

    public void setColumn(String files, String name) {
        this.column.add(new Column(files, name));
    }

    public void setTitle(String title) {
        if (!Strings.isNullOrEmpty(title)) {
            this.title = title;
        }
    }
    public void setFileName(String fileName) {
        if (!Strings.isNullOrEmpty(fileName)) {
            this.fileName = fileName;
        }
    }

    public void setDataFormat(String dataFormat) {
        if (!Strings.isNullOrEmpty(title)) {
            this.dataFormat = dataFormat;
        }
    }

    public void setVersion(ExcelVersion excelVersion) {
        if (Objects.nonNull(excelVersion)) {
            this.version = excelVersion;
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {
        /**
         * excel表头对应的字段
         */
        private String field;

        /**
         * excel 表头中文名称
         */
        private String name;
    }

}
