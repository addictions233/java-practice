package com.one.batch;

/**
 * @ClassName: ProducerTest
 * @Description: 测试使用kafka的producer发送消息
 * @Author: one
 * @Date: 2022/03/22
 */
public class ProducerTest {
    public static void main(String[] args) {
        KafkaProducerBatch kafkaProducerBatch = new KafkaProducerBatch();
        kafkaProducerBatch.sendMsg();
    }
}
