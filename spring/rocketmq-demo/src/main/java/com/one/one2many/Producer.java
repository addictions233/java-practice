package com.one.one2many;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: Producer
 * @Description: 生产者产生消息  此处是一个消息生产者产生多条消息对多个消费者
 * @Author: one
 * @Date: 2020/12/19
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1,创建一个发送消息的对象producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2设定发送消息的命名服务器
        producer.setNamesrvAddr("124.220.21.120:9876");
        //3,启动发送消息的服务
        producer.start();
        //消息生产者发送多条消息
        for (int i = 0; i < 10; i++) {
            //4,创建要发送的消息对象
            Message msg = new Message("topicB",("hello! rocketmq "+i).getBytes(StandardCharsets.UTF_8));
            //5,发送消息
            producer.send(msg);
        }
        //6,关闭资源,即发送消息的对象
        producer.shutdown();
    }
}
