package com.one.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RabbitMQConfig
 * @Description: 使用RabbitMQ的配置类: 这里使用topic通配符模式, routingKey路由Key为: item.# 以item开头的都能匹配
 * @Author: one
 * @Date: 2021/04/05
 */
@Configuration
public class RabbitMQConfig {
    // 定义交换机名称
    public static final String ITEM_TOPIC_EXCHANGE = "item_topic_exchange";
    // 定义队列名称
    public static final String ITEM_QUEUE = "item_queue";

    /**
     * 声明交换机对象, 通配符模式下: 交换机Exchange也是 topic通配符类型
     */
    @Bean("itemTopicExchange")
    public Exchange itemTopicExchange(){
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).build();
    }

    /**
     * 声明队列对象,
     */
    @Bean("itemQueue")
    public Queue itemQueue(){
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }

    /**
     * 将队列绑定到交换机上, 注意要指定 routingKey路由Key,这里指定为 item.*
     */
    @Bean
    public Binding queueBindingExchange(@Qualifier("itemQueue") Queue queue,
                                        @Qualifier("itemTopicExchange")Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
    }
}
