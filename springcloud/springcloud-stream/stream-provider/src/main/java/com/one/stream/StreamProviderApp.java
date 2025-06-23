package com.one.stream;

import com.one.stream.config.CustomChannelBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @ClassName: StreamProvider
 * @Description: 启动类
 * @Author: one
 * @Date: 2022/05/12
 */
@SpringBootApplication
@EnableBinding({CustomChannelBinder.class})
public class StreamProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(StreamProviderApp.class, args);
    }
}
