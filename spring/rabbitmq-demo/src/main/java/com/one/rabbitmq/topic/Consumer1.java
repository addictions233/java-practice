package com.one.rabbitmq.topic;

import com.one.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Consumer1
 * @Description: 通配符模式下的消费者1,
 * @Author: one
 * @Date: 2021/04/05
 */
public class Consumer1 {
    // 定义交换机名称
    private static final String DIRECT_EXCHANGE = "direct_exchange";
    // 定义消息队列名称
    private static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1, 创建连接,获取连接对象
        Connection connection = ConnectionUtil.getConnection();
        // 2, 创建频道对象
        Channel channel = connection.createChannel();
        // 3, 声明交换机, 交换机类型: direct 定向
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 4, 声明队列
        channel.queueDeclare(DIRECT_QUEUE_INSERT,true,false,false,null);
        // 5, 对交换机绑定队列 指定路由key: "insert"
        channel.queueBind(DIRECT_QUEUE_INSERT,DIRECT_EXCHANGE,"insert");
        //6, 设置每次从消息队列中获取的消息数量, 这里设定为1
        channel.basicQos(1);
        // 7, 注册监听器, 设置消息处理方法
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消息id:"+ envelope.getDeliveryTag());
                System.out.println("路由key:" + envelope.getRoutingKey());
                System.out.println("交换机exchange:"+ envelope.getExchange());
                System.out.println("消息内容:"+ new String(body));
                // 确认消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        // 8,开启监听
        channel.basicConsume(DIRECT_QUEUE_INSERT,true,consumer);
    }
}
