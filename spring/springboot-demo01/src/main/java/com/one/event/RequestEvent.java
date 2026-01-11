package com.one.event;

import org.springframework.context.ApplicationEvent;

/**
 * 定义一个事件对象: Spring所有的事件都继承自ApplicationEvent
 */
public class RequestEvent extends ApplicationEvent {
    public RequestEvent(Object source) {
        super(source);
    }
}
