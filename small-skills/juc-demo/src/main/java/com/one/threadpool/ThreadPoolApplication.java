package com.one.threadpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName: ThreadPoolApplication
 * @Description: 启动类
 * @Author: one
 * @Date: 2021/05/07
 * @EnableAsync 在SpringBoot启动类上添加该注解, 这样就可以使用异步线程池
 * @EnableScheduling 启动定时任务
 */
@SpringBootApplication
@EnableAsync // 开启异步支持
@EnableScheduling // 开启定时任务支持
public class ThreadPoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThreadPoolApplication.class,args);
    }
}
