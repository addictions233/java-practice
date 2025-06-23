package com.one.cosumer;

import com.alibaba.fastjson.JSON;
import com.one.contstant.TopicConstant;
import com.one.domain.User;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Consumer
 * @Description: 使用RocketMq的消费端只用注册一个消息队列的监听器, 然后写一个消息处理方法,这样就能对消息进行处理
 * @Author: one
 * @Date: 2020/12/22
 */
@Component
@RocketMQMessageListener(consumerGroup = "Groupx",topic = TopicConstant.USER_TOPIC)
public class UserConsumer implements RocketMQListener<User> {
    /**
     * 注册监听函数(相当于registryMessageListener)
     *     消费者在broker中注册监听器,当消息队列中有该消费者的主题消息时就调用回调函数进行消费
     */
    @Override
    public void onMessage(User user) {
            String json = JSON.toJSONString(user);
            System.out.println("consumer消费到的消息:" + json);
    }
}
