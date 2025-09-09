package com.one.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @description:   自定义分区器
 *          测试kafka的分区路由机制
 *                   1. Producer会根据消息的key选择Partition，具体如何通过key找Partition呢??
 *                   2. 一个消费者组会共同消费一个Topic下的多个Partition中的同一套消息副本，那Consumer节点是不是
 *                     可以决定自己消费哪些Partition的消息呢？
 * @author: wanjunjie
 * @date: 2024/10/28
 */
public class SinglePartitioner implements Partitioner {

    /**
     * 决定一个消息如何根据Key分配到对应的
     * Partition上的。你甚至可以很简单的实现一个自己的分配策略
     * Compute the partition for the given record.
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
                         Cluster cluster) {
        // 获取topic下的所有分区信息
        List<PartitionInfo> partitionInfoList = cluster.partitionsForTopic("topic");
        int num = partitionInfoList.size();
        // 随机选择一个分区
//        int partition = new Random().nextInt(num);
        // 选择第一个分区
        return 0;
    }

    /**
     * This is called when partitioner is closed.
     */
    @Override
    public void close() {

    }

    /**
     * Configure this class with the given key-value pairs
     *
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {

    }
}
