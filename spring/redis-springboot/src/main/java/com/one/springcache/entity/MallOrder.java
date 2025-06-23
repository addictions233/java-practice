package com.one.springcache.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: 实体类
 * @author: wanjunjie
 * @date: 2024/10/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MallOrder {
    /**
     * id
     */
    private Long id;

    /**
     * 订单号
     */
    private String oderNo;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 单价
     */
    private BigDecimal price;
}
