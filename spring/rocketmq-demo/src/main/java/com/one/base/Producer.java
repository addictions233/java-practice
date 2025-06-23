package com.one.base;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: Producer
 * @Description: 生产者产生消息
 * @Author: one
 * @Date: 2020/12/19
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1,创建一个发送消息的对象producer, 指定生产者的组名
        DefaultMQProducer producer = new DefaultMQProducer("producer-group1",false);
        //2,设定发送消息的命名服务器
        producer.setNamesrvAddr("127.0.0.1:9876");
        //3,启动发送消息的服务
        producer.start();
        //4,创建要发送的消息对象, 所有的消息都要指定topic, consumer监听对应的topic进行消费
        Message msg = new Message("topicA","hello! rocketmq".getBytes(StandardCharsets.UTF_8));
        // Topic 与 Tag 都是业务上用来归类的标识，区分在于 Topic 是一级分类，而 Tag 可以理解为是二级分类
        // 如果消息指定了Tag, 消费者中可以通过tag来进行过滤自己需要消费的消息
//        Message msg = new Message("topicA","tag1","hello! rocketmq".getBytes(StandardCharsets.UTF_8));
        //5,发送消息
        SendResult sendResult = producer.send(msg);
        System.out.println("发送消息结果:" + sendResult);
        //6,关闭资源,即发送消息的对象
        producer.shutdown();
    }
}
