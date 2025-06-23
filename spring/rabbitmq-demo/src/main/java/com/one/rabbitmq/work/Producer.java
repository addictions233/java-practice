package com.one.rabbitmq.work;

import com.one.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Producer
 * @Description: 工作队列模式下的生产者: 在同一个队列中可以有多个消费者,消费者之间对于消息是竞争关系
 * @Author: one
 * @Date: 2021/04/04
 */
public class Producer {
    // 定义队列名称
    private static final String QUEUE_NAME = "work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1, 创建连接连接工厂,获取连接对象
        Connection connection = ConnectionUtil.getConnection();
        // 2, 创建频道channel
        Channel channel = connection.createChannel();
        // 3,声明队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        // 4,发送消费
        for (int i = 0; i < 20; i++) {
            String message = "work model for rabbitmq,消息ID:"+ i;
            /**
             * 参数1 String exchange: 交换机名称, 如果没有则用空字符串(表示使用默认的交换机)
             * 参数2 String routingKey: 路由key, 简单模式中可以使用队列名称来替代
             * 参数3 BasicProperties props: 消息的其他属性
             * 参数4 byte[] body: 消息的内容
             */
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        }
        // 5,关闭资源
        channel.close();
        connection.close();
    }
}
