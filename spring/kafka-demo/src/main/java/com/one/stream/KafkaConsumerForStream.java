package com.one.stream;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: KafkaConsumerForStream
 * @Description: TODO
 * @Author: one
 * @Date: 2021/02/21
 */
public class KafkaConsumerForStream {
    private static final String OUTPUT_TOPIC = "out_topic";

    public static void main(String[] args) {
        //添加配置信息
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "8.135.28.120:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //设置分组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group2");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        //创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        //订阅主题
        consumer.subscribe(Collections.singletonList(OUTPUT_TOPIC));


        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.key()+":"+record.value());
                /*try {
                    //手动提交偏移量
                    consumer.commitSync();
                }catch (CommitFailedException e){
                    e.printStackTrace();
                    System.out.println("记录错误信息为："+e);
                }*/

            }
            consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                    if(e!=null){
                        System.out.println("记录当前错误信息时提交的偏移量"+map+",异常信息为："+e);
                    }
                }
            });
        }
    }
}
