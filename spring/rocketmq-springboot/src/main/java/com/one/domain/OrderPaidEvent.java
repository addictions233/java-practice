package com.one.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author ：one
 * @description: 订单支付事件
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaidEvent {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 货物ID
     */
    private String productId;


    /**
     * 支付金额
     */
    private BigDecimal paidMoney;

}
