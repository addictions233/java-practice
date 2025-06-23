package com.one.order2;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: Consumer
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/20
 */
public class Consumer {
    public static void main(String[] args) throws Exception{
        //1,创建消息的消费对象consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2,指定消费对象的命名服务器
        consumer.setNamesrvAddr("127.0.0.1:9876");
        //3,为消费者订阅topic
        consumer.subscribe("orderTopic","*");
        //4,消费者对象在rocketmq中注册监听
        // 默认的情况下一个Consumer会有多达十几个线程,每个线程都去MessageQueue中并发的获取消息并执行
        // 那么消息的执行就没有先后顺序,我们现在要做的是让一个MessageQueue中的消息进入同一线程
        // list集合为当前Consumer对象分配到的message对象组成的集合
        consumer.registerMessageListener((MessageListenerOrderly) (list, consumeOrderlyContext) -> {
            for (MessageExt messageExt : list) {
                System.out.println(new String(messageExt.getBody()));
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        //5,启动消费者
        consumer.start();
    }
}
