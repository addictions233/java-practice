package com.one.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.one.config.MyTransactionRocketMQTemplate;
import com.one.contstant.TopicConstant;
import com.one.domain.OrderPaidEvent;
import com.one.domain.OrderSystem;
import com.one.domain.StorageSystem;
import com.one.service.OrderPaidService;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description: 处理用户的支付订单事件
 * @author: wanjunjie
 * @date: 2024/04/26
 */
@Service
public class OrderPaidServiceImpl implements OrderPaidService {

    @Resource
    private MyTransactionRocketMQTemplate myTransactionRocketMQTemplate;

    public static final ConcurrentMap<String, OrderSystem> orderSystemMap = new ConcurrentHashMap<>();

    public static final ConcurrentMap<String, StorageSystem> storageSystemMap = new ConcurrentHashMap<>();

    static  {
        orderSystemMap.put("oderId_001", new OrderSystem("oderId_001","productId_005",2, 0));
        storageSystemMap.put("productId_005", new StorageSystem("productId_005", "智能手机-小米-15pro",800));
    }


    /**
     * 主分支订单系统支付状态更新：订单支付状态由未支付变更为支付成功
     *
     * @param orderPaidEvent 订单支付事件
     */
    @Override
    public void handleOrderPaidEvent(OrderPaidEvent orderPaidEvent) {
        // 1. send transaction message
        org.springframework.messaging.Message<OrderPaidEvent> message = MessageBuilder
                .withPayload(orderPaidEvent)
                .build();
        String destination = TopicConstant.ORDER_TRANSACTION_TOPIC + ":" + "tagA";
        TransactionSendResult sendResult = this.myTransactionRocketMQTemplate.sendMessageInTransaction(destination, message, destination);
        System.out.println("发送半消息状态:" + sendResult);
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            throw new RuntimeException("发送订单事务消息失败");
        }
    }


    @RocketMQTransactionListener(rocketMQTemplateBeanName = "myTransactionRocketMQTemplate")
    static class OrderPaidTransactionListener implements RocketMQLocalTransactionListener {

        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(org.springframework.messaging.Message msg, Object arg) {
            // 2. local transaction work
            try {
                System.out.println("executeLocalTransaction执行了...");
                byte[] bytes = (byte[]) msg.getPayload();
                OrderPaidEvent orderPaidEvent = JSON.parseObject(bytes, OrderPaidEvent.class);
                String orderId = orderPaidEvent.getOrderId();
                OrderSystem orderSystem = orderSystemMap.get(orderId);
                System.out.println( 1 / 0);
                // 将订单支付状态改为1-已支付
                orderSystem.setPayStatus(1);
            } catch (Exception e) {
                System.out.println("订单系统修改支付状态失败");
                // 3.1 rollBack half message
//                return RocketMQLocalTransactionState.ROLLBACK;
                return RocketMQLocalTransactionState.UNKNOWN;
            }
            // 3.1 commit half message
            return RocketMQLocalTransactionState.COMMIT;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(org.springframework.messaging.Message msg) {
            // send check request
            System.out.println("checkLocalTransaction 执行了...");
            byte[] bytes = (byte[]) msg.getPayload();
            OrderPaidEvent orderPaidEvent = JSON.parseObject(bytes, OrderPaidEvent.class);
            String orderId = orderPaidEvent.getOrderId();
            OrderSystem orderSystem = orderSystemMap.get(orderId);
            if (orderSystem.getPayStatus() == 1) {
                // commit half message
                return RocketMQLocalTransactionState.COMMIT;
            }
            // check unknown transaction
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
