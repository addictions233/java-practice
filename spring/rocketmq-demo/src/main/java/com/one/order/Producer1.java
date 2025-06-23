package com.one.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName: Producer1
 * @Description: 同一个生产者产生的消息是有序的,必须有序让消费者执行
 * @Author: one
 * @Date: 2020/12/20
 */
public class Producer1 {
    public static void main(String[] args) throws Exception {
        //1,创建一个发送消息的生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2,设置发送消息的命名服务器
        producer.setNamesrvAddr("124.220.21.120:9876");
        //3,启动发送消息的服务
        producer.start();
        //4, 创建多条需要发送的消息Message对象
        for (int i = 1; i <= 10; i++) {
            Message message = new Message("orderTopic",("order1: hello rocketmq!"+i).getBytes(StandardCharsets.UTF_8));
            //5, 生产者发送消息Message
            // 一个Topic的message默认会分别进入一个rocketmq的四个队列queue, queueId =0,1,2,3
//            SendResult sendResult = producer.send(message);
            // 利用MessageQueueSelector指定同一类型的message进入同一个队列queue
            // list 四个队列对象组成的集合
            SendResult sendResult = producer.send(message, (list, message1, arg) -> {
                // 该producer发送的所有消息直接指定同一个messageQueue
                // 该producer产生的message进入queueId=0的队列中,由于均衡负载原则该message最终进入consumer1
                return list.get(0);
            }, null);
            // 打印发送消息之后的结果
            System.out.println(sendResult);
        }
        //6, 关闭资源,关闭生产者对象
        producer.shutdown();
    }

}
