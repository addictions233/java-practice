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
public class Producer2 {
    public static void main(String[] args) throws Exception {
        //1,创建一个发送消息的生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2,设置发送消息的命名服务器
        producer.setNamesrvAddr("124.220.21.120:9876");
        //3,启动发送消息的服务
        producer.start();
        //4, 创建多条需要发送的消息Message对象
        for (int i = 1; i <= 10; i++) {
            Message message = new Message("orderTopic",("order2: hello rocketmq!"+i).getBytes(StandardCharsets.UTF_8));
            //5, 生产者发送消息Message
//            producer.send(message);
            // queueId=0,1的队列中的message进入第一个Consumer , queueId=2,3的队列中的消息进入第二个Consumer
            SendResult sendResult = producer.send(message, (list, message1, arg) -> {
                //该producer产生的message进入queueId=2的队列中,由于均衡负载原则,该message最终进入consumer2
                return list.get(2);
            }, null);
            System.out.println(sendResult);
        }
        //6, 关闭资源,关闭生产者对象
        producer.shutdown();
    }

}
