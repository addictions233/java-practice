package com.one.kafla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: KafkaApplication
 * @Description: 在SpringBoot项目中集成kafka
 * @Author: one
 * @Date: 2021/01/29
 */
@SpringBootApplication
public class KafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class,args);
    }
}
