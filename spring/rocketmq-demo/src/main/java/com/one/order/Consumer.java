package com.one.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassName: Consumer
 * @Description: 消费者对同一个生产者产生的消息必须有序的处理
 * @Author: one
 * @Date: 2020/12/20
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        //1, 创建处理消息的消费者对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2,为消费者指定命名服务器
        consumer.setNamesrvAddr("124.220.21.120:9876");
        //3,为消费者订阅topic
        consumer.subscribe("orderTopic", "*");
        //4,消费者在mq中注册监听, 监听rocketmq
        // 让同一个MessageQueue队列的message进入consumer的同一个线程中, 这样才能实现顺序消费
        consumer.registerMessageListener((MessageListenerOrderly) (list, consumeOrderlyContext) -> {
            for (MessageExt messageExt : list) {
                System.out.println(Thread.currentThread().getName() + "消费了" + new String(messageExt.getBody()));
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        // 如果消费者指定为并发消费的模式,同一个MessageQueue中的消息也会被多个线程消费,无法实现顺序消费
//        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
//            for (MessageExt messageExt : list) {
//                System.out.println(new String(messageExt.getBody()));
//            }
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        });
        //5,启动消费者
        consumer.start();
    }
}
