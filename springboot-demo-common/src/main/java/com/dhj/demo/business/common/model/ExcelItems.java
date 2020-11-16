package com.dhj.demo.business.common.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 商品表
 * </p>
 *作为Excel导出模板类
 * @author 邓怀俊
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExcelItems implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelIgnore
    private Long id;

    /**
     * 鞋名
     */
    @ExcelProperty(value = "鞋名",index = 0)
    private String shoeName;

    /**
     * 城市名称
     */
    @ExcelProperty(value = "城市名称",index = 1)
    private String cityName;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格",index = 2)
    private Integer price;

    /**
     * 数量
     */
    @ExcelProperty(value = "库存数量",index = 3)
    private Integer number;


}
