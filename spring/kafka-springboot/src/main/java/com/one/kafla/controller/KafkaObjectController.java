package com.one.kafla.controller;

import com.alibaba.fastjson.JSON;
import com.one.kafla.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: KafkaObjectController
 * @Description:  发送对象为Object类型的消息数据
 * @Author: one
 * @Date: 2021/01/29
 */
@RestController
@RequestMapping("/producer")
public class KafkaObjectController {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping("/object")
    public String sendObjectMessage(){
        Person person = new Person("张三",23,"男");
        this.kafkaTemplate.send("kafka-object-topic", JSON.toJSONString(person));
        return "发送成功";
    }
}
