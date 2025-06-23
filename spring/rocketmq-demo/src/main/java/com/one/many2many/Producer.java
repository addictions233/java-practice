package com.one.many2many;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: Producer
 * @Description: 生产者产生消息,一个生产者可以生产多条消息,可以有多个消息生产者
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
        for (int i = 1; i <= 10; i++) {
            //4,创建要发送的消息对象
            Message msg = new Message("topicC",("生产者2:hello! rocketmq "+i).getBytes(StandardCharsets.UTF_8));
            //5,发送消息
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        }
        //6,关闭资源,即发送消息的对象
        producer.shutdown();
    }
}
