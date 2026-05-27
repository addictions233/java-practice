package com.one.basic;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * Kafka支持通过seek()方法来让consumer手动提交消费偏移量
 * 这样在出现消息积压之后, 重启Consumer我们可以取我们想要开始消费的消息偏移量
 */
public class KafkaConsumerSeekDemo {

    private static final String TOPIC = "kafka-one";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 设置key和value的反序列化器, producer进行序列化, consumer进行反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 设置consumer的群组, Consumer是按照消费者组记录消费进度的
        // 一条消息只能被一个消费者组中的一个Consumer进行消费, 但是能被多个消费者组进行消费
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group10");

        // 如果broker
        // 如果broker服务端没有保存该消费者组提交的消费偏移量
        // earliest: 从分区最早的消息开始消费（包括历史数据）
        // latest(默认值): 从分区最新的消息开始消费, 历史消息不消费
        // none: 如果broker端没有该消费者组的消费偏移量, 则抛出异常
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 开启自动提交offset
//        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); // 关闭自动提交offset, 需要进行手动提交
        // 自动提交offset是异步提交, 可以设置自动提交间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10000");

        // ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG是max.poll.interval.ms，表示最大轮询间隔时间，
        // 若手动设置为500，意味着消费者在两次连续轮询之间最多只能等待500毫秒。
        // 如果超过该最大轮询时间，消费者将被认为已经失去连接，从而触发重新平衡操作，将其分配给其他消费者。
        // 该参数如果设置较小，可能会导致频繁重新平衡，而消费者本身没有问题的情况下，设置过小反而影响频繁导致该消费者无法正常工作，就会抛出以上异常。
        // 但是，若设置过大的话，可能导致消费者在长时间无法处理新的记录。需要根据业务处理时长来决定这个参数值
//        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "500");
        // poll 每批拉取的最大消费的消息数量
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5");

        // 创建消费者
        // 一个Partition最多只能同时被一个Consumer消费。
        // 也就是说，如果有四个Partition的Topic，那么同一个消费者组中最多就只能配置四个消费者实例。
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        //订阅主题, 可以订阅多个主题
        // 如果该消费者组有新的消费者加入, kafka的consumer会进行动态负载均衡
        consumer.subscribe(Collections.singletonList(TOPIC), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("---onPartitionsRevoked-----");
                for (TopicPartition partition : partitions) {
                    System.out.println("partition:" + partition);
                }
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("---onPartitionsAssigned-----");
                for (TopicPartition partition : partitions) {
                    System.out.println("partition:" + partition);
                }
            }
        });

        // 使用seek方法手动提交消费偏移量
        Set<TopicPartition> topicPartitionSet = consumer.assignment();

        while (topicPartitionSet.isEmpty()) {
            // 如果consumer不调用poll方法拉取方法, 没有和broker通信, 获取topicPartitionSet为空
            consumer.poll(Duration.ofMillis(1000));
            topicPartitionSet = consumer.assignment();
        }

        Map<TopicPartition, Long> tts = new HashMap<>();
        for (TopicPartition topicPartition : topicPartitionSet) {
            tts.put(topicPartition, System.currentTimeMillis() - 10*1000);
        }
        // 通过时间戳timestamp取回消费偏移量offset
        Map<TopicPartition, OffsetAndTimestamp> offTimeMap = consumer.offsetsForTimes(tts);
        for (TopicPartition topicPartition : topicPartitionSet) {
            // 获取每个分区的时间戳timestamp对应的偏移量offset
            OffsetAndTimestamp offsetAndTimestamp = offTimeMap.get(topicPartition);
            long offset = offsetAndTimestamp.offset();
            // 使用seek()方法提交consumer在对应分区的偏移量
            consumer.seek(topicPartition, offset);
        }


        while (true) {
            // 消费者主动向broker拉消息, 也就是拉模式, 超时时间1000ms
            // 一次拉取的是一批消息
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            System.out.println("消息总数:" + records.count());

            // 消费消息
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("topic:" + record.topic()); // topic主题
                System.out.println("partition:" + record.partition());  // partition 分区
                System.out.println("key:" + record.key()); // 消息key
                System.out.println("消息内容:" + record.value()); // 消息内容
            }

            // 同步提交, 表示必须等到offset提交完毕之后, 再去消费下一批数据
            consumer.commitSync();
        }

    }
}
