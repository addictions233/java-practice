package com.one.consumer;

import com.one.api.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * @author one
 * 配置消费端的服务调用超时时间, 如果消费端超过时间还没有收到响应结果, 则消费端会抛出超时异常
 */
@EnableAutoConfiguration
public class TimeoutDubboConsumerDemo {


    /**
     * 配置客户端的调用超时时间为3s
     */
    @Reference(version = "timeout", timeout = 1000)
    private DemoService demoService;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(TimeoutDubboConsumerDemo.class);

        DemoService demoService = context.getBean(DemoService.class);

        // 服务调用超时时间为1秒，默认为3秒
        // 如果这1秒内没有收到服务结果，则会报错
        System.out.println((demoService.sayHello("周瑜")));


    }

}
