package com.one.kafla.consumer;

import com.alibaba.fastjson.JSON;
import com.one.kafla.pojo.Person;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @ClassName: KafkaConsumer
 * @Description: kafka的消费端
 * @Author: one
 * @Date: 2021/01/29
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "kafka-topic")
    public void receiveMessage(ConsumerRecord record){
        Optional<ConsumerRecord> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            Object value = record.value();
            System.out.println(value);
        }
    }

    /**
     * 当发送的消息为Object对象,先转json格式字符串,再转字节数组发送,在消费者端解析json格式字符串为Object对象
     * @param record
     */
    @KafkaListener(topics = "kafka-object-topic")
    public void receiveObject(ConsumerRecord record){
        Optional<ConsumerRecord> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            String jsonValue = (String)record.value();
            Person person = JSON.parseObject(jsonValue, Person.class);
            // 输出: Person(name=张三, age=23, gender=男)
            System.out.println(person);
        }
    }
}
