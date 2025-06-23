package com.one.basic;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName: KafkaProducer
 * @Description: 消息生产者
 *    分为: producer 生产者, broker 中间管理,  consumer 消费者
 * @Author: one
 * @Date: 2021/01/29
 */
public class KafkaProducerDemo {
    /**
     * 定义producer发送的消息的topic主题,只有订阅这个topic主题的consumer才能消费到这条消息
     */
    private static final String TOPIC = "kafka-one";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 添加kafka的配置信息, ProducerConfig客户端的配置类
        Properties properties = new Properties();
        //配置kafka的broker服务器信息
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 分别设置key和value的序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // 配置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 10);
        // 配置生产者的拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "com.one.interceptor.KafkaProducerInterceptor");


        //生产者producer对象,
        // 第一个泛型String是发送消息的key, Key用来指定消息路由到哪个分区partition中,先对key取hash值,然后利用hash值对partition的个数取模
        // 这样就能确定这个key对应的消息路由到哪个分区partition, 如果没有指定消息的key,就用轮询的方式选取一个分区partition
        // 第二个泛型String是发送消息的内容
        KafkaProducer<String,String> producer = new KafkaProducer<>(properties);

        // 创建消息记录（包含主题、partition、key、消息本身）  (String topic, Integer partition, K key, V value)
        // 封装消息 包含主题topic, key, 消息内容本身, 有partition用partition,没有partition用key的hash值确定partition,两个都没有用轮询的方式确定partition
        // 消息的分区partition保证了消息被顺序消费, 读取效率更高
        ProducerRecord<String,String> record = new ProducerRecord<>(TOPIC,"00001","hello kafka !");

        // 1.单向发送消息,不关心服务器应答, 可靠性不高
//        producer.send(record);

        // 2.同步发送, 获取服务端的响应之前会阻塞当前线程
        RecordMetadata recordMetadata = producer.send(record).get();
        String topic = recordMetadata.topic();
        int partition = recordMetadata.partition();
        long offset = recordMetadata.offset();
        String message = recordMetadata.toString();
        System.out.println("message:" + message + ",topic:" + topic + ",partition:" + partition + ",offset:" + offset);

        // 3.异步发送,消息发送后不阻塞, 带回调函数的消息发送,broker在接收到消息后会调用callback中的回调函数
        producer.send(record, (metadata, exception) -> {
            System.out.println("topic:" + metadata.topic()); // 消息的topic
            System.out.println("partition:" + metadata.partition()); // 消息的分区partition
            System.out.println("offset:" + metadata.offset()); // 消息的偏移量offset
            System.out.println("message:" + metadata.toString());
        });

        //关系消息通道
        producer.close();
    }
}
