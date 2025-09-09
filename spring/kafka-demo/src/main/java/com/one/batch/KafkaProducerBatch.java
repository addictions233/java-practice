package com.one.batch;

import com.one.partition.SinglePartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


/**
 * @ClassName: KafkaProducerDemo2
 * @Description: 消息生产方
 * @Author: one
 * @Date: 2021/03/31
 */
public class KafkaProducerBatch {
    /**
     * 设置发送的消息的 TOPIC
     */
    private static final String TOPIC = "kafka_topic";

    /**
     * kafka发送消息的对象
     */
    private KafkaProducer<Integer, String> kafkaProducer;

    public KafkaProducerBatch() {
        Properties prop = new Properties();
        // 配置连接信息
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"39.108.89.252:9092");
        // 配置key的序列化方式
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerSerializer");
        // 配置value的序列化方式
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        // 配送发送消息的重试次数
        prop.put(ProducerConfig.RETRIES_CONFIG,10);
        // 自定义分区器
        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, SinglePartitioner.class);
        this.kafkaProducer = new KafkaProducer<>(prop);
    }

    /**
     * 使用producer发送消息
     */
    public void sendMsg() {
        // 使用的是topic,key, value的方式构造要发送的消息
        ProducerRecord<Integer, String> record = new ProducerRecord<>(TOPIC,1,"wuhan");
        // 使用kafkaProducer对象发送消息
        kafkaProducer.send(record, (metadata, exception) -> {
            System.out.println("topic:" + metadata.topic()); // 消息的topic
            System.out.println("partition:" + metadata.partition()); // 消息的分区partition
            System.out.println("offset:" + metadata.offset()); // 消息的偏移量offset
        });

        // 关闭连接
        kafkaProducer.close();
    }
}
