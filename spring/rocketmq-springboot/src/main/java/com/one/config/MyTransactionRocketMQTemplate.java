package com.one.config;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * @author ：one
 * @description: 使用自定义的rocketMQTemplate发送事务消息
 **/
@ExtRocketMQTemplateConfiguration()
//@ExtRocketMQConsumerConfiguration(topic="stock_consumer_group")
public class MyTransactionRocketMQTemplate extends RocketMQTemplate {
}
