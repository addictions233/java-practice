package com.one.basic;

/**
 * Kafka支持通过seek()方法来让consumer手动提交消费偏移量
 * 这样在出现消息积压之后, 重启Consumer我们可以取我们想要开始消费的消息偏移量
 */
public class KafkaConsumerSeekDemo {
}
