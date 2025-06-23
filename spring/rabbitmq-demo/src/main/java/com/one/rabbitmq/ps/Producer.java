package com.one.rabbitmq.ps;

import com.one.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Producer
 * @Description: 发布与订阅模式: 生产者, 工作队列模式下,一个队列中的消息只能被一个消费者消费,且多个消费者之间是竞争关系,
 *               如果我们想让一条消息被多个消费者消费,那么我们就需要使用发布订阅模式,就一条消息通过交换机exchange发布到
 *               多个消息队列中,然后被多个消费者消费
 * @Author: one
 * @Date: 2021/04/04
 */
public class Producer {
    /**
     *  定义交换机名称 Exchange交换机,
     *    三种模式: fanout(广播), direct(定向), topic(通配符)
     */
    private static final String FANOUT_EXCHANGE = "fanout_exchange";
    /**
     * 定义消息队列名称  发布订阅模式至少有两条消息队列
     */
    private static final String FANOUT_QUEUE_1 = "fanout_queue_1";
    private static final String FANOUT_QUEUE_2 = "fanout_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1, 创建连接工厂,获取连接
        Connection connection = ConnectionUtil.getConnection();
        // 2, 创建频道
        Channel channel = connection.createChannel();
        // 3, 声明交换机
        /**
         * 参数1 String exchange: 交换机名称
         * 参数2 BuiltinExchangeType type: 交换机类型,一共三种 fanout, direct ,topic
         */
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        // 4, 声明队列
        channel.queueDeclare(FANOUT_QUEUE_1,true,false,false,null);
        channel.queueDeclare(FANOUT_QUEUE_2,true,false,false,null);
        // 5, 队列绑定到交换机
        channel.queueBind(FANOUT_QUEUE_1,FANOUT_EXCHANGE,"");
        channel.queueBind(FANOUT_QUEUE_2,FANOUT_EXCHANGE,"");
        // 6, 发送消息
        for (int i = 0; i < 10; i++) {
            String message = "this is publish and subscribe, id:" + i;
            /**
             * 参数1 String exchange: 交换机名称
             * 参数2 String routingKey: 路由key , 如果没有使用可以使用空白串来替代(表示使用默认的路由key)
             * 参数3 BasicProperties: 消息的其他属性
             * 参数4 byte[] body: 消息内容
             */
            channel.basicPublish(FANOUT_EXCHANGE,"",null,message.getBytes());
        }
        //7, 关闭资源
        channel.close();
        connection.close();
    }
}
