package com.one.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 订单系统的订单信息
 * @author: wanjunjie
 * @date: 2024/04/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSystem {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 货物ID
     */
    private String productId;

    /**
     * 订单的货物数量
     */
    private Integer productCount;

    /**
     * 订单的支付状态: 0-未支付 1-已支付
     */
    private Integer payStatus;
}
