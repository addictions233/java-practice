package com.one.producer;

import com.one.contstant.TopicConstant;
import com.one.domain.User;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author one
 * @description TODO
 * @date 2023-2-18
 */

@Component
public class UserProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendUserMessage() {
        User user = new User(1,"张三",(byte) 23);
        Message<User> message = MessageBuilder.withPayload(user).build();
        SendResult sendResult = rocketMQTemplate.syncSend(TopicConstant.USER_TOPIC, message);
        // 同步发送顺序消息
//        rocketMQTemplate.syncSendOrderly(TopicConstant.USER_TOPIC,message, String.valueOf(user.getId()));
        if (SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            System.out.println("发送成功");
        } else {
            System.out.println("发送失败");
        }
    }
}
