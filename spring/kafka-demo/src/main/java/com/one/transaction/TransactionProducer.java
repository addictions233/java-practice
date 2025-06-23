package com.one.transaction;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ProducerFencedException;

import javax.swing.*;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 测试生产者的消息事务
 * @auther one
 */
public class TransactionProducer {
    private static final String BOOTSTRAP_SERVERS = "worker1:9092,worker2:9092,worker3:9092";
    private static final String TOPIC = "disTopic";
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        // 此处配置的是kafka的端口
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        // 事务ID。
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"111");
        // 配置key的序列化类
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        // 配置value的序列化类
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        Producer<String,String> producer = new KafkaProducer<>(props);
        // 批量发生消息保证事务, 一批消息要么同时发送成功, 要么同时发送失败
        producer.initTransactions();
        producer.beginTransaction();
        try{
            for(int i = 0; i < 5; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, Integer.toString(i), "MyProducer" + i);
                //异步发送。
                producer.send(record);
            }
            // 提交事务
            producer.commitTransaction();
        }catch (ProducerFencedException e){
            // 回滚事务
            producer.abortTransaction();
        }finally {
            producer.close();
        }
    }
}
