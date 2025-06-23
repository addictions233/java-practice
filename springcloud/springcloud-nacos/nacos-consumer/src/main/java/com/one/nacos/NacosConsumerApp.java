package com.one.nacos;

import com.one.nacos.config.MyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @ClassName: NacosConsumerApplication
 * @Description: 客户端, 服务消费者
 * @Author: one
 * @Date: 2022/04/05
 */
@SpringBootApplication
//@EnableDiscoveryClient  // 可以省略该注解
//@RibbonClient(name = "nacos-provider", configuration = MyRule.class)  // 用注解的方式配置Ribbon的负载均衡策略, 可以为不同的服务指定不同的策略
public class NacosConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApp.class, args);
    }
}
