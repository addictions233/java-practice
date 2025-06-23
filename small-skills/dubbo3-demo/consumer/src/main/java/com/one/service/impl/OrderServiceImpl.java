package com.one.service.impl;

import com.one.api.HelloService;
import com.one.api.UserService;
import com.one.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author one
 * @description 测试对dubbo3服务的引入
 * @date 2024-11-17
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

//    @DubboReference(protocol = "dubbo") // 测试对dubbo协议的支持
    @DubboReference(protocol = "rest") // 测试对rest协议的支持
//    @DubboReference(protocol = "tri") // 测试对triple协议的支持
    private HelloService helloService;

    @DubboReference(protocol = "tri", check = false) // check = false表示启动时不检查provider服务是否可用
    private UserService userService;

    @Override
    public String sayHello(String name) {
        return helloService.sayHello(name);
    }

    @Override
    public void sayHelloServerStream(String name) {
        userService.sayHelloServerStream(name, new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("接收到的响应数据:" + data);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("响应数据接收完毕!");
            }
        });
    }

    @Override
    public void sayHelloStream(String name) {
        StreamObserver<String> streamObserver = userService.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("接收到的响应数据:" + data);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                System.out.println("响应数据接收完毕!");
            }
        });
        streamObserver.onNext(name);
        streamObserver.onNext("request lisi");
        streamObserver.onNext("requst wangwu");
        streamObserver.onCompleted();
    }

    @Override
    public String createOrder() {
        return userService.getUser("123").getUsername();
    }
}
