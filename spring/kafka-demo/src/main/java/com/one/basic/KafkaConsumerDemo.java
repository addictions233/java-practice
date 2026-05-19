package com.one.basic;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;

/**
 * @ClassName: KafkaConsumerDemo
 * @Description: 消费者, kafka的生产者和消费者都是java代码的服务器, 而broker是kafka中间件起的服务, 采用scala编写
 * @Author: one
 * @Date: 2021/01/29
 */
public class KafkaConsumerDemo {
    private static final String TOPIC = "kafka-one";

    public static void main(String[] args) {
        // 添加配置信息
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

                // 第一种: 按照每条消息的粒度来提交偏移量, 最安全, 但是效率最低
                HashMap<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                map.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()));
                // 这种最安全, 每条消息记录级别的offset维护
                consumer.commitSync();
            }

            // kafka消费有个问题: 消费进度偏移量offset是由consumer提交的, 但是是记录在broker的,
            // 所以如果consumer提交消费进度失败或者错误, 会导致broker记录错误的consumer消费进度, 造成消息消费的丢失或者重复
            for (TopicPartition topicPartition : records.partitions()) {
                System.out.println("topic:" + topicPartition.topic() + ",partition:" + topicPartition.partition());

                // 从records中获取当前topicPartition的全部消息记录
                // 每个分区中的消息是有序的
                List<ConsumerRecord<String, String>> partitionRecords = records.records(topicPartition);

                // 如果我们要避免消费进度offset在consumer和broker的管理不一致, 可以客户端在保存一份自己的消费进度偏移量,对比broker端的消费进度
                // 使用topic + partition 作为key, 记录当前partition的消费进度偏移量
                String redisKey = topicPartition.topic() + "#" + topicPartition.partition();
                // 获取最后一条消息的偏移量
                long redisValue = partitionRecords.get(partitionRecords.size() - 1).offset();
                // TODO 将 redisKey和redisValue放入缓存中, 这样就可以在客户端记录自己真实消费的偏移量
                // TODO 后续消费对比自己记录的消费偏移量和broker给的消息的偏移量进行对比, 如果不一致以自己记录的为主, 丢掉重复消费的

                // 第二种: 按照分区partition的维度来手动提交offset
                // 获取该分区最后一条消息的偏移量, 作为该partition提交的offset
                long offset = partitionRecords.get(partitionRecords.size() - 1).offset();
                HashMap<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                map.put(topicPartition, new OffsetAndMetadata(offset));
                // 由于broker是按照分区的维度来维护offset, 所以推荐采用这种方式来提交偏移量
                consumer.commitSync(map);
            }


            // 提交消费offset, 消息就不会重复推送了
            // 这个Offset偏移量，需要消费者处理完成后主动向Kafka的Broker提交。
            // 提交完成后，Broker就会更新消费进度，表示这个消息已经被这个消费者组处理完了。
            // 但是如果消费者没有提交Offset，Broker就会认为这个消息还没有被处理过，就会重新往对应的消费者组进行推送，
            // 不过这次，一般会尽量推送给同一个消费者组当中的其他消费者实例。
            // 第三种: 按照整个poll拉取的消息批次来提交offset
            consumer.commitSync(); // 同步提交, 表示必须等到offset提交完毕之后, 再去消费下一批数据

//            consumer.commitAsync(); // 异步提交, 表示发送完提交offset请求后, 就开始消费下一批数据了, 不用等到Broker的确认
        }

    }

    /**
     * 如果是手动提交offset
     * 1.按照消息的粒度进行同步提交
     * 2.按照分区partition的维护进行同步提交
     * 3.按照当前poll的批次来同步提交 (如果一个consumer对应消费多个partition, 就会一次poll到多个partition的消息)
     *
     * 如果在多个线程下消费是不是有问题???
     */
}
