package com.one.kafla.db;

import com.one.kafla.config.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类说明：使用Kafka提供服务，实现（削峰填谷）流量整形
 */
@Component
public class ConsumerService {
    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 3, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
    private static final List<KafkaConsumer<String,String>> consumers = new ArrayList<>();

    @Autowired
    private DBService dbService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaConsumerConfig kafkaConsumerConfig;


    private static class ConsumerWorker implements Runnable {

        private final KafkaConsumer<String, String> consumer;
        private final DBService dbService;

        private final KafkaTemplate<String, String> kafkaTemplate;

        public ConsumerWorker(KafkaConsumer<String, String> consumer, DBService dbService, KafkaTemplate<String, String> kafkaTemplate) {
            this.consumer = consumer;
            this.dbService = dbService;
            this.kafkaTemplate = kafkaTemplate;
            /*因为连接池为2个大小，所以限定主题为2个分区，所以消费者也只有2个*/
            consumer.subscribe(Collections.singletonList("traffic-shaping"));
        }

        public void run() {
            final String id = Thread.currentThread().getId()
                    + "-" + System.identityHashCode(consumer);
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println(id+"|"+String.format(
                                "主题：%s，分区：%d，偏移量：%d，" +
                                        "key：%s，value：%s",
                                record.topic(),record.partition(),
                                record.offset(),record.key(),record.value()));
                        System.out.println("开始购票业务－－－－－－");
                        String result = dbService.useDb("select ticket");
                        System.out.println(result+"，准备通知客户端");
                        /*主题为10个分区大小，可以更大，因为客户端那边没有削峰的需要，
                        如果需要，一样处理即可*/
                        kafkaTemplate.send("traffic-shaping-result", result);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 3; i++) {
            // 启动3个消费者
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaConsumerConfig.consumerConfigs());
            executorService.submit(new ConsumerWorker(consumer, dbService, kafkaTemplate));
            consumers.add(consumer);
        }
    }

    @PreDestroy
    public void destroy() {
        for (KafkaConsumer<String, String> consumer : consumers) {
            consumer.close();
        }
    }


}
