package com.one.basic;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: KafkaConsumerDemo
 * @Description: 消费者
 *         kafka的生产者和消费者都是java代码的服务器, 而broker是kafka中间件起的服务
 * @Author: one
 * @Date: 2021/01/29
 */
public class KafkaConsumerDemo {
    private static final String TOPIC = "kafka-one";

    public static void main(String[] args) {
        // 添加配置信息, ConsumerConfig服务端的配置类
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 设置key和value的反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        // 设置consumer的群组, Consumer是按照消费者组记录消费进度的
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group2");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 创建消费者
        // 一个Partition最多只能同时被一个Consumer消费。
        // 也就是说，如果有四个Partition的Topic，那么同一个消费者组中最多就只能配置四个消费者实例。
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        //订阅主题, 可以订阅多个主题
        consumer.subscribe(Collections.singletonList(TOPIC));

        while (true) {
            // 消费者主动向broker拉消息, 也就是拉模式, 超时时间1000ms
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            // kafka消费有个问题: 消费进度偏移量offset是由consumer提交的, 但是是记录在broker的, 所以如果consumer提交消费进度失败或者错误
            // 会导致broker记录错误的consumer消费进度, 造成消息消费的丢失或者重复
            // 如果我们要避免消费进度offset在consumer和broker的管理不一致, 可以客户端在保存一份自己的消费进度偏移量,对比broker端的消费进度
            records.partitions().forEach(topicPartition -> {
                String redisKey = topicPartition.topic() + topicPartition.partition();
                List<ConsumerRecord<String, String>> partitionRecords = records.records(topicPartition);
                long redisValue = partitionRecords.get(partitionRecords.size() - 1).offset();
                // TODO 将 redisKey和redisValue放入缓存中, 这样就可以在客户端记录自己真实消费的偏移量
                // TODO 后续消费对比自己记录的消费偏移量和broker给的消息的偏移量进行对比, 如果不一致以自己记录的为主, 丢掉重复消费的
            });

            // 消费消息
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("topic:" + record.topic()); // topic主题
                System.out.println("partition:" + record.partition());  // partition 分区
                System.out.println("key:" + record.key()); // 消息key
                System.out.println("消息内容:" + record.value()); // 消息内容
            }

            // 提交消费offset, 消息就不会重复推送了
            // 这个Offset偏移量，需要消费者处理完成后主动向Kafka的Broker提交。
            // 提交完成后，Broker就会更新消费进度，表示这个消息已经被这个消费者组处理完了。
            // 但是如果消费者没有提交Offset，Broker就会认为这个消息还没有被处理过，就会重新往对应的消费者组进行推送，
            // 不过这次，一般会尽量推送给同一个消费者组当中的其他消费者实例。
            consumer.commitSync(); // 同步提交, 表示必须等到offset提交完毕之后, 再去消费下一批数据

            consumer.commitAsync(); // 异步提交, 表示发送完提交offset请求后, 就开始消费下一批数据了, 不用等到Broker的确认
        }

    }
}
