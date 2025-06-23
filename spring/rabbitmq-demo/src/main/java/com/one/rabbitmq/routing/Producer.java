package com.one.rabbitmq.routing;

import com.one.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Producer
 * @Description: 路由模式下的消息生产者, 发布订阅模式下消息在交换机中会以广播的形式发送到所有与交换机绑定的消息队列中,如果我们
 * 想要将消息发送到与交换机绑定的指定的消息队列中,我们就需要为消息指定 RoutingKey,只有消息的路由key和消息队列的路由key完全一致时
 * 才会让消息队里接收到此条消息
 * @Author: one
 * @Date: 2021/04/05
 */
public class Producer {
    // 定义交换机名称, 交换机采用direct定向模式
    private static final String DIRECT_EXCHANGE = "direct_exchange";
    // 定义消息队列名称
    private static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    private static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1, 创建连接工厂,获取连接对象
        Connection connection = ConnectionUtil.getConnection();
        // 2,创建频道
        Channel channel = connection.createChannel();
        // 3,声明交换机 路由模式下 交换机的类型指定为 direct 定向
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 4, 声明队列
        channel.queueDeclare(DIRECT_QUEUE_INSERT,true,false,false,null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE,true,false,false,null);
        // 5, 为交换机绑定消息队列
        /**
         * 消息中路由key为insert的会被绑定到路由key为insert的队列接收并被其监听的消费者接收处理
         * 消息中路由key为update的会被绑定到路由key为update的队列接收并被其监听的消费者接收处理
         */
        channel.queueBind(DIRECT_QUEUE_INSERT,DIRECT_EXCHANGE,"insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE,DIRECT_EXCHANGE,"update");
        //6, 创建并发送消息  注意路由模式下一定要指定消息的routingkey 路由Key
        String message1 = "this is routing model and routingKey is insert";
        channel.basicPublish(DIRECT_EXCHANGE,"insert",null,message1.getBytes());
        String message2 = "this is routing model and routingKey is update";
        channel.basicPublish(DIRECT_EXCHANGE,"update",null,message2.getBytes());
        //7, 关闭资源
        channel.close();
        connection.close();
    }
}
