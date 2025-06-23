package com.one.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * kafka提供了拦截器
 * @author one
 */
public class KafkaProducerInterceptor implements ProducerInterceptor {

    /**
     * 发送消息时触发
     */
    @Override
    public ProducerRecord onSend(ProducerRecord producerRecord) {
        System.out.println("producerRecord : " + producerRecord.toString());
        return producerRecord;
    }

    /**
     * 收到服务端ACK响应时触发
     */
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        System.out.println("acknowledgement recordMetadata:"+recordMetadata.toString());
    }

    /**
     * 连接关闭时触发
     */
    @Override
    public void close() {
        System.out.println("producer closed");
    }

    /**
     * 整理配置项
     */
    @Override
    public void configure(Map<String, ?> map) {
        // 打印producer的配置信息
        System.out.println("=====config start======");
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            System.out.println("entry.key:"+entry.getKey()+" === entry.value: "+entry.getValue());
        }
        System.out.println("=====config end======");
    }
}
