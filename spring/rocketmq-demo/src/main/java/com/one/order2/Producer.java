package com.one.order2;

import com.one.domain.Order;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Producer
 * @Description: 要保证发送到broker中的消息被顺序消费, 需要做到以下两点:
 *                       1,需要顺序消费的消息需要进入同一个MessageQueue消息队列中,
 *                       可以在发送消息时使用MessageQueueSelector指定消息需要进入的messageQueue
 *                       2,消费者需要使用同一个线程去消费同一MessageQueue中的消息,
 *                       在Consumer中指定监听器为顺序监听器 MessageListenerOrderly
 *              顺序消费处理消息的效率肯定比并发的处理消息的效率要低
 * @Author: one
 * @Date: 2020/12/20
 */
public class Producer {
    public static void main(String[] args) throws Exception{
        //1,创建一个生产者对象
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2,指定生产者的命名服务器
        producer.setNamesrvAddr("127.0.0.1:9876");
        //3,生产者启动发送消息的服务
        producer.start();
        //4,创建需要的发送的消息对象message
        //创建要执行的业务队列, 需要按照: 主单 --> 子单 --> 支付  ---> 推送  顺序消费消息
        List<Order> orderList = new ArrayList<Order>();
        Order order11 = new Order();
        order11.setId("a");
        order11.setMsg("主单-1");
        orderList.add(order11);

        Order order12 = new Order();
        order12.setId("a");
        order12.setMsg("子单-2");
        orderList.add(order12);

        Order order13 = new Order();
        order13.setId("a");
        order13.setMsg("支付-3");
        orderList.add(order13);

        Order order14 = new Order();
        order14.setId("a");
        order14.setMsg("推送-4");
        orderList.add(order14);

        Order order21 = new Order();
        order21.setId("b");
        order21.setMsg("主单-1");
        orderList.add(order21);

        Order order22 = new Order();
        order22.setId("b");
        order22.setMsg("子单-2");
        orderList.add(order22);

        Order order31 = new Order();
        order31.setId("c");
        order31.setMsg("主单-1");
        orderList.add(order31);

        Order order32 = new Order();
        order32.setId("c");
        order32.setMsg("子单-2");
        orderList.add(order32);

        Order order33 = new Order();
        order33.setId("c");
        order33.setMsg("支付-3");
        orderList.add(order33);
        // 遍 list集合将order对象转换为字符串封装到message对象中
        for (Order order : orderList) {
            Message message = new Message("orderTopic",order.toString().getBytes());
            SendResult sendResult = producer.send(message, (list, message1, arg) -> {
                // 直接对集合取模存在的问题: 如果Broker不稳定, 宕机, 集合的长度发生变化, 取模的结果就会发生变化
                int index = order.getId().hashCode() % list.size();
                return list.get(index);
            }, null);
            System.out.println(sendResult);
        }
        //6,关流
        producer.shutdown();
    }
}
