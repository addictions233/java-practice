package com.one.stream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: kafkaProducerForStream
 * @Description: 测试KafkaStream的入门案例的 消息生产者
 * @Author: one
 * @Date: 2021/02/21
 */
public class kafkaProducerForStream {
    private static final String INPUT_TOPIC = "input_topic";

    public static void main(String[] args) {
        //添加kafka的配置信息
        Properties properties = new Properties();
        //配置broker信息
        properties.put("bootstrap.servers", "8.135.28.120:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.RETRIES_CONFIG, 10);

        //生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        try {
            //封装消息
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    ProducerRecord<String, String> record =
                            new ProducerRecord<String, String>(INPUT_TOPIC, i + "", "hello shanghai kafka stream hello");
                    //发送消息
                    producer.send(record);
                    System.out.println("发送消息：" + record);
                } else {
                    ProducerRecord<String, String> record =
                            new ProducerRecord<String, String>(INPUT_TOPIC, i + "", "helloworld kafka stream");
                    //发送消息
                    producer.send(record);
                    System.out.println("发送消息：" + record);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //关系消息通道
        producer.close();
    }
}
