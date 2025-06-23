package com.one.rabbitmq;

import com.one.rabbitmq.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: ProducerTest
 * @Description: 生产者producer发送三条消息, 这三条消息的路由key分别是item.insert,item.update,item.delete
 * 在消费者端查看是否消费到了上面三条路由Key对应的消息
 * @Author: one
 * @Date: 2021/04/05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {
    /**
     * Spring提供的一个操作RabbitMQ的模板类, 注意RabbitMQ的使用配置类是我们自己编写的
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessages(){
        /**
         * 参数1 String exchange: 交换机名称
         * 参数2 String routingKey: 路由Key
         * 参数3 Object object: 消息内容
         */
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE,"item.insert",
                "商品新增,路由Key: item.insert");
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE,"item.insert",
                "商品修改,路由Key: item.update");
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE,"item.insert",
                "商品删除,路由Key: item.delete");

    }

}
