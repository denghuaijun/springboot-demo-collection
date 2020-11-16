package com.dhj.demo.mp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author 邓怀俊
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 鞋名
     */
    private String shoeName;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 数量
     */
    private Integer number;


}
