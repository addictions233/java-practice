package com.one.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Producer
 * @Description: rabbitmq简单模式下的生产者
 * @Author: one
 * @Date: 2021/04/04
 */
public class Producer {
    /**
     * 定义一个队列名称常量
     */
    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1, 创建连接工厂, 设置Rabbitmq的连接参数
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 主机:默认localhost
        connectionFactory.setHost("192.168.31.114");
        // 连接端口: 5672
        connectionFactory.setPort(5672);
        // 虚拟主机: 默认/  可以从rabbtimq的图形化管理工具中查看
        connectionFactory.setVirtualHost("my_vhost");
        // 用户名: 默认guest
        connectionFactory.setUsername("admin");
        // 密码: 默认guest
        connectionFactory.setPassword("admin");

        // 2, 使用连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3, 创建频道channel
        Channel channel = connection.createChannel();

        // 4,声明队列
        /**
         * 参数1 String queue: 队列名称
         * 参数2 boolean durable: 是否持久化(消息会持久化保存在服务器上)
         * 参数3 boolean exclusive: 是否独占本连接
         * 参数4 boolean autoDelete: 是否在不使用的时候队列自动删除
         * 参数5 Map<String,Object> arguments: 其他参数
         */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        // 5, 发送消息
        /**
         * 参数1 String exchange : 交换机名称,如果没有则指定为空字符串(空字符串表示使用默认的交换机)
         * 参数2 String routingKey: 路由key,简单模式中可以使用队列名称
         * 参数3 BasicProperties props: 消息其他属性
         * 参数4 byte[] body: 消息内容(字节数组)
         */
        String message= "hello rabbitmq!";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());

        // 6, 关闭资源
        channel.close();
        connection.close();
    }
}
