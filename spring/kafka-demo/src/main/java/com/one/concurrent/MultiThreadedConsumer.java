package com.one.concurrent;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/**
 * 当你部署2个微服务实例，每个实例的concurrency=2时，每个实例确实会创建2个消费者线程，整个消费组（TEST_GRP_ID）下总共有4个消费者线程。
 * 但这里要注意Kafka的核心规则：同一个消费组内，一个分区只能被一个消费者线程消费。
 * 你的topic只有2个分区，所以最终只有2个线程会被分配到分区进行消费，
 * 剩下的2个线程会处于空闲状态（等着分区重新分配，比如某个实例挂了的时候）。
 * 至于具体哪两个线程分到分区，取决于你用的分区分配策略（默认是Range，也可以配置成RoundRobin）。
 * 通常我们的服务实例数是大于broker的数量的,
 * 我们更推荐: 一个线程启动Consumer拉取消息, 多个业务线程处理消息, 最后统一由consumer线程提交偏移量offset
 * 这样能提升消费的效率, 但是违背同一个分区的消息顺序处理的逻辑
 */
public class MultiThreadedConsumer {
    
    private static final KafkaConsumer<String, String> consumer;
    private static final ExecutorService executor;

    static {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "c_test");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<>(properties);
        executor =  Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    private final Map<TopicPartition, OffsetAndMetadata> pendingOffsets =
        new ConcurrentHashMap<>();
    private final Map<TopicPartition, Semaphore> partitionLocks =
        new ConcurrentHashMap<>();
    
    public void run() {
        consumer.subscribe(Arrays.asList("topic"));
        
        while (true) {
            // consumer主线程拉取消息
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            // 如果一个consumer对应多个分区
            List<CompletableFuture<Void>> list = new ArrayList<>();
            for (TopicPartition partition : records.partitions()) {
                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);

                // 多个业务线程处理消息业务
                // 每个分区一个线程处理，保证分区内的顺序
                // 同一个消费组内，一个分区只能被一个消费者线程消费。
                list.add(CompletableFuture.runAsync(() -> processPartition(partition, partitionRecords), executor));
            }
            // 等待所有的线程把业务处理结束
            CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
            // 由consumer主线程来提交偏移量
            this.commitOffsets();
        }
    }
    
    private void processPartition(TopicPartition partition,
                                  List<ConsumerRecord<String, String>> partitionRecords) {
        // 获取该分区的锁，保证同一分区只有一个线程在处理
        Semaphore lock = partitionLocks.computeIfAbsent(partition, k -> new Semaphore(1));
        
        try {
            lock.acquire();
            
            long lastOffset = -1;
            for (ConsumerRecord<String, String> record : partitionRecords) {
                try {
                    System.out.println("topic:" + record.topic() + ",partition:" + record.partition() + ",msg:" + record.value());
                    // 取最后一条消息的偏移量, 就是
                    lastOffset = record.offset();
                } catch (Exception e) {
                    // 关键：单条失败如何处理？
                    // 方案1：失败即停止该分区（最保守）
                    // 方案2：跳过失败，记录死信（需业务允许无序/丢失）
                    handleFailure(record, e);
                    
                    // 如果决定跳过，lastOffset 仍然更新
                    // 如果不跳过，break 且不提交 offset
                    break; 
                }
            }
            
            // 只提交成功处理的最后一条
            if (lastOffset >= 0) {
                pendingOffsets.put(partition, new OffsetAndMetadata(lastOffset + 1));
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.release();
        }
    }
    
    // 定时提交或同步提交
    private void commitOffsets() {
        Map<TopicPartition, OffsetAndMetadata> snapshot;
        synchronized (pendingOffsets) {
            snapshot = new HashMap<>(pendingOffsets);
            pendingOffsets.clear();
        }
        consumer.commitSync(snapshot); // 批量提交
    }

    private void handleFailure(ConsumerRecord<?, ?> record, Exception e) {
        // 发送到 DLQ topic
//        dlqProducer.send(new ProducerRecord<>("topic.DLQ", record.key(), record.value()));

        // 记录日志，监控报警
        System.out.println("Message failed after retries");

        // 继续提交 offset，保证消费进度
        // 但业务数据进入 DLQ，需要人工介入或自动重处理
    }
}