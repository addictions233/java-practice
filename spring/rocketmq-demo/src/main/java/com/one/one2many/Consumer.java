package com.one.one2many;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @ClassName: Consumer
 * @Description: 消费者接受消息,启动多个消费者对单个生产者生产出来多条消息进行处理
 * @Author: one
 * @Date: 2020/12/19
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        //1,创建一个消费者对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2,为消费者对象指定命名服务器
        consumer.setNamesrvAddr("127.0.0.1:9876");
        //3,为消费者指定topic, *对应的sub标签为任意
        consumer.subscribe("topicB","*");

        // 默认就是负载均衡模式
        //负载均衡模式: 设置当前消费者的消费模式为负载均衡模式,(默认的消费模式是: 负载均衡),两个consumer对消息进行平均分配
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //广播模式: 设置当前消费者的消费模式为广播模式,所有客户端接收到的消息都是一样的, 每个consumer都接收所有的消息
//        consumer.setMessageModel(MessageModel.BROADCASTING);

        //4,注册监听,监听rocketmq
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            for (MessageExt messageExt : list) {
//                    System.out.println(messageExt);
                byte[] body = messageExt.getBody();
                System.out.println(new String(body));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //5,启动消费者对象
        consumer.start();
    }
}
