package com.one.cosumer;

import com.one.contstant.TopicConstant;
import com.one.domain.OrderPaidEvent;
import com.one.domain.OrderSystem;
import com.one.domain.StorageSystem;
import com.one.service.impl.OrderPaidServiceImpl;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/04/26
 */
@RocketMQMessageListener(consumerGroup = "storageSystem",
        topic = TopicConstant.ORDER_TRANSACTION_TOPIC,
        maxReconsumeTimes = 3,
        consumeThreadNumber = 3)
public class StorageSystemConsumer implements RocketMQListener<OrderPaidEvent> {

    @Override
    public void onMessage(OrderPaidEvent orderPaidEvent) {
        String orderId = orderPaidEvent.getOrderId();
        OrderSystem orderSystem = OrderPaidServiceImpl.orderSystemMap.get(orderId);
        String productId = orderPaidEvent.getProductId();
        StorageSystem storageSystem = OrderPaidServiceImpl.storageSystemMap.get(productId);
        synchronized (OrderPaidServiceImpl.orderSystemMap) {
            storageSystem.setStoreCount(storageSystem.getStoreCount() - orderSystem.getProductCount());
        }
    }
}
