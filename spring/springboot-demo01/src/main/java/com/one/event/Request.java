package com.one.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 构建一个请求: 在处理请求时发布事件
 */
@Component
public class Request {

    /**
     * ApplicationContext继承了ApplicationEventPublisher, 可以用来发布消息
     */
    @Autowired
    private ApplicationContext applicationContext;

    public void doRequest() {
        System.out.println("调用了doRequest方法, 发送一个事件请求..");
        // 发送一个消息
        applicationContext.publishEvent(new RequestEvent(this));
    }
}
