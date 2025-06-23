package com.one.rabbitmq.simple;

import com.one.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: consumer
 * @Description: rabbitmq简单模式下的消费者
 * @Author: one
 * @Date: 2021/04/04
 */
public class Consumer {
    // 定义队列名称, 注意: 消费者consumer中的队列名称要和生产者producer中的消息名称一致,这样才能消费消息
    private static final String QUEUE_NAME  = "simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1, 获取连接工厂,创建连接
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 2,声明队列
        /**
         * 第一个参数 String queue: 指定队列名称
         * 第二个参数 boolean durable: 是否进行持久化
         * 第三个参数 boolean exclusive: 是否是独占连接
         * 第四个参数 boolean autoDelete: 是否在不使用的时候自动删除队列
         * 第五个参数 Map<String,Object> arguments 其他参数
         */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        // 3, 创建消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            // 采用匿名内部类重写父类方法,进行消息消费
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 打印路由的key
                System.out.println("路由的key为:" + envelope.getRoutingKey());
                // 打印交换机
                System.out.println("交换机为:" + envelope.getExchange());
                // 打印消息id
                System.out.println("消息id为:"+ envelope.getDeliveryTag());
                // 接收到的消息类容 body即字节数组形式的消息内容
                System.out.println("接收到的消息类容为:" + new String(body));
            }
        };
        // 4, 监听队列
        /**
         * 参数1 String queue: 队列名
         * 参数2 boolean autoAck: 是否自动确认, 设置为true表示消息接收到自动向MQ队列回复接收到了消息,MQ会将消息从队列中删除
         *     如果设置为false 则需要手动确认
         * 参数3 消费者对象
         */
        channel.basicConsume(QUEUE_NAME,true,consumer);

    }
}
