package com.one.rabbitmq.work;

import com.one.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Consumer1
 * @Description: 消费者1, 创建两个消费者监听同一个队列,查看两个消费者的接收消息是否重复
 * @Author: one
 * @Date: 2021/04/04
 */
public class Consumer1 {
    // 定义队列名称
    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1, 创建连接工厂,获取连接对象
        Connection connection = ConnectionUtil.getConnection();
        // 2, 创建频道对象
        Channel channel = connection.createChannel();
        // 3,声明队列
        /**
         * 参数1 String queue: 队列名称
         * 参数2 boolean durable: 是否进行持久化
         * 参数3 boolean exclusive: 是否独占本连接
         * 参数4 boolean autoDelete: 是否在不使用队列的时候自动删除
         * 参数5 Map<String,Object> arguments 其他参数
         */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        // 设置每次预取的消息数量, 这里设置每次只能取一条消息
        channel.basicQos(1);

        // 4, 创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println(new String(body));
                    // 消费完一条消息,休眠1秒钟
                    Thread.sleep(1000);
                    //确认消息, 消费方需要返回ack
                    /**
                     * 参数1 long deliveryTag: 消息id
                     * 参数2 boolean multiple: false表示只有当前这条被处理
                     */
                    channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        // 5,监听队列
        /**
         * 参数1 String queue: 队列名称
         * 参数2 boolean autoAck: 是否自动返回确认,如果是true, 则消费者在接收消息之后自动返回确认,消息队列就删除该条消息
         */
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);

    }
}
