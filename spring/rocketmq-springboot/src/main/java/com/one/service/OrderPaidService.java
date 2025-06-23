package com.one.service;

import com.one.domain.OrderPaidEvent;

/**
 * @description:
 * @author: wanjunjie
 * @date: 2024/04/26
 */
public interface OrderPaidService {

    /**
     * 主分支订单系统支付状态更新：订单支付状态由未支付变更为支付成功
     * @param orderPaidEvent
     */
    void handleOrderPaidEvent(OrderPaidEvent orderPaidEvent);
}
