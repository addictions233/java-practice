package com.one.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyMessageListener
 * @Description: 在消费者端定义消息监听器,注意消费者端的消息队列名称要和生产者的消息队列名称一致
 * 注意: 我们在消费者端是没有声明交换机Exchange和路由Key, 只是指定了消息队列名称为item_queue
 * 消费者端是自动去该消息队列中消费消息的
 * @Author: one
 * @Date: 2021/04/05
 */
@Component
public class MyMessageListener {
    // 定义队列名称
    private static final String ITEM_QUEUE = "item_queue";

    /**
     * @RabbitListener: Spring提供的注解,用来对消息队列进行监听, 需要在该注解的属性中指定队列名称
     * @param message 消息内容
     */
    @RabbitListener( queues = ITEM_QUEUE)
    public void myMessageHandler(String message){
        System.out.println("消费者端接收到的消息内容为:"+ message);
    }
}
