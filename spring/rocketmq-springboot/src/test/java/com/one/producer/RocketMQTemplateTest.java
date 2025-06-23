package com.one.producer;

import com.one.domain.User;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName: ProducerTest
 * @Description: 测试SpringBoot整合rocketMQ
 * @Author: one
 * @Date: 2020/12/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketMQTemplateTest {
    /**
     * 自动创建的producer对象:  producer发送消息
     */
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void send(){
        User user = new User();
        user.setId(2);
        user.setUserName("wangdachui2342");
        user.setUserAge((byte) 20);
        rocketMQTemplate.convertAndSend("topicm",user);

    }
}
