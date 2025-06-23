package com.one.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassName: Consumer
 * @Description: 测试事务消息: 事务消息和consumer没有关系, 只保证 producer执行本地事务 和 broker接收到消息 的事务一致性
 * @Author: one
 * @Date: 2020/12/19
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        //1,创建一个消费者对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2,为消费者对象指定命名服务器
        consumer.setNamesrvAddr("124.220.21.120:9876");
        //3,为消费者指定topic
        consumer.subscribe("topicY","*");
        //4,注册监听,监听rocketmq,一旦mq中有消息,就发送到Consumer中
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            // 钩子函数, Consumer对象设置的供broker调用的回调函数
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    System.out.println(messageExt);
                    byte[] body = messageExt.getBody();
                    System.out.println(new String(body));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5,启动消费者对象
        consumer.start();
    }
}
