package com.one.kafla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: KafkaController
 * @Description: kafka的消息的生产者, 发送String数据类型的message
 * @Author: one
 * @Date: 2021/01/29
 */
@RestController
@RequestMapping("/producer")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping("/send")
    public String sendMessages(){
        this.kafkaTemplate.send("kafka-topic","这是一个悲伤的开始!");
        return "发送成功";
    }
}
