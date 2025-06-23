package com.one.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: VideoServiceApp
 * @Description: 启动类
 * @Author: one
 * @Date: 2022/04/10
 */
@SpringBootApplication
@MapperScan("com.one.video.dao") // 使用@Repository注解必须结合@MapperScan使用,指定包扫描的路径
public class VideoServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(VideoServiceApp.class, args);
    }
}
