package com.one.kafla.consumer;

import com.alibaba.fastjson.JSON;
import com.one.kafla.pojo.Person;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
//import org.springframework.kafka.retrytopic.SameIntervalTopicReuseStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Optional;

/**
 * @ClassName: KafkaConsumer
 * @Description: kafka的消费端
 * @Author: one
 * @Date: 2021/01/29
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "kafka-topic", containerFactory = "kafkaListenerContainerFactory")
    public void receiveMessage(ConsumerRecord<String, String> record){
        Optional<ConsumerRecord<String, String>> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            Object value = record.value();
            System.out.println(value);
        }
    }

    /**
     * 虽然使用 RetryableTopic 的异步处理优势为我们带来了性能提升，但这种使用也有一些缺点。
     * 使用RetryableTopic可能会破坏消息的处理顺序。
     * 让我们用一个例子来解释这种情况：当主主题在时间 t 处理时，一条消息出错并被发送到重试主题。在时间 t + 1 时，另一条消息来到主主题并成功处理。
     * 让我们在重试主题中的消息在时间 t + 2 时被成功处理。在这种情况下，第一条传入消息将在第二条消息之后处理。
     * 如果订购对您很重要，我建议您在消息处理过程中进行必要的检查。
     * 另一个缺点是消息双重处理的风险。您可以通过考虑这种可能性来进行改进。
     * @param record
     */
    @RetryableTopic(
            backoff = @Backoff(delay = 1000, multiplier = 2.0), // 退避时间: 用于确定处理消息的时间间隔。从 Backoff 类获取一个值。
            attempts = "3",  // 尝试处理消息的次数。它的默认值为 3。如果完成所有尝试后仍然收到错误，则消息将发送到 DLT 队列。
//            sameIntervalTopicReuseStrategy = SameIntervalTopicReuseStrategy.SINGLE_TOPIC, // 用于确定要创建的重试主题策略。创建 (SINGLE_TOPIC) 或尽可能多的尝试值 (MULTIPLE_TOPICS) 重试主题。
            kafkaTemplate = "kafkaTemplate", // 将消息发送到重试topic或者死信队列时使用的 KafkaTemplate bean 名称。
            exclude = {SerializationException.class, IllegalArgumentException.class,
            NullPointerException.class} // 允许您排除指定的异常类。当您添加到列表中的任何错误被抛出时，重试机制将不会被激活。
    )
    @KafkaListener(topics = "kafka-object-topic")
    public void receiveObject(ConsumerRecord<String, String> record){
        Optional<ConsumerRecord<String, String>> optional = Optional.ofNullable(record);
        if(optional.isPresent()){
            String jsonValue = (String)record.value();
            Person person = JSON.parseObject(jsonValue, Person.class);
            // 输出: Person(name=张三, age=23, gender=男)
            System.out.println(person);
        }
    }

    /**
     * 如果完成了定义的尝试次数并且继续收到错误，则消息将发送到 DLT 队列。如果要处理这些消息，可以使用DltHandler注解。
     * @param record 消息
     */
    @DltHandler
    public void dltHandler(ConsumerRecord<String, String> record){
        System.out.println("DLT Consumer: " + record);
    }
}
