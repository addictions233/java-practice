package com.one.stream;

import com.one.stream.config.CustomChannelBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @ClassName: StreamConsumer
 * @Description: 启动类
 * @Author: one
 * @Date: 2022/05/12
 */
@SpringBootApplication
@EnableBinding({CustomChannelBinder.class})
public class StreamConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(StreamConsumerApp.class, args);
    }
}
