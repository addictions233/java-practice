package com.one.batch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @ClassName: KafkaConsumerDemo2
 * @Description: 消息的消费方
 * @Author: one
 * @Date: 2021/04/01
 */
public class KafkaConsumerBatch {
    // 设置消费消息的TOPIC
    private static final String TOPIC = "kafka_topic";
    public static void main(String[] args) {
        // 使用Properties对象来设置属性
        Properties prop = new Properties();
        // 设置kafka服务地址
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"39.108.89.252:9092");
        // 设置消费端的消息的key的反序列化器(kafka自带的)
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerDeserializer");
        // 设置消息端消息的value的反序列化器
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        // 消费端最重要的是要设置一个group组  注意: 同一个组里面只能对同一个TOPIC的消息消费一次,不能重复消费
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
        prop.put("auto.offset.reset", "earliest");
        KafkaConsumer<Integer,String> consumer = new KafkaConsumer<>(prop);
        // 消费端订阅主题
        consumer.subscribe(Collections.singletonList(TOPIC));
        while (true){
            // 每个1000毫秒从分区中拉去消息来进行消费
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<Integer, String> record : records) {
                // 输出: kafka_topic
                System.out.println(record.topic());
                // 输出: 25
                System.out.println(record.partition());
                // 输出:  one
                System.out.println(record.key());
                // 输出:  hello kafka
                System.out.println(record.value());
            }
        }
    }
}
