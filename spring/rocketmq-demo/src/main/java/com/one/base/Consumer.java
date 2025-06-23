package com.one.base;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @ClassName: Consumer
 * @Description: 消费者接受消息
 * @Author: one
 * @Date: 2020/12/19
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        //1,创建一个mq消息的消费者对象
        // mq一旦有了消息broker就主动向Consumer推送消息,主动权在broker中,实际开发中用pushconsumer
        // 1. 如果启动同一个消费者组(consumerGroup)的不同消费者,默认采用负载均衡的方式,每个消费者监听各自的messageQueue(消费者的数量不要超过
        // Topic的messageQueue的数量),所以每个消费者只消费自己对应的messageQueue中的消费
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group3",true);

        //消费者Consumer主动从mq中拉去消息,主动权在consumer中,但消费者不知道多长时间去mq中拉取消息
//        DefaultMQPullConsumer consumer1 = new DefaultMQPullConsumer("group1")
        //2,为消费者对象指定命名服务器
        consumer.setNamesrvAddr("127.0.0.1:9876");
        //3,为消费者指定topic, * 表示订阅所有的tag
        consumer.subscribe("topicA","*");
        // 只定义topicA主题下的tag为tag1和tag2的消息
//        consumer.subscribe("topicA","tag1||tag2");
        //4,注册监听器,监听rocketmq , 一旦broker中有消息,就调用回调函数
        // 钩子函数, Consumer对象设置的供broker调用的钩子函数(broker监听到消息就调用钩子函数)
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, consumeConcurrentlyContext) -> {
            for (MessageExt messageExt : msgs) { // 取出消息并进行打印
//                System.out.println(messageExt);
                byte[] body = messageExt.getBody();
                System.out.println(new String(body, StandardCharsets.UTF_8));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //5,启动消费者对象
        consumer.start();
    }
}
