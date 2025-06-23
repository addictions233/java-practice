package com.one.netty.eventloop;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author one
 * @description 学习netty核心组件 EventLoop
 * @date 2024-4-4
 */
@Slf4j
public class EventLoopTest {
    public static void main(String[] args) {
        // 1.创建事件循环组, EventLoop只有一个线程,可以处理io,事件,普通任务,定时任务
        // 一个EventLoopGroup中包含多个EventGroup,所以一个EventLoopGroup管理多个线程, 可以看做一个线程组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
        // 只能处理普通任务和定时任务
//        EventLoopGroup eventLoopGroup1 = new DefaultEventLoop();
        System.out.println(eventLoopGroup.next());
        System.out.println(eventLoopGroup.next());
        System.out.println(eventLoopGroup.next());

        // 因为EventGroup继承了ScheduledExecutorService,可以用来执行异步任务
        eventLoopGroup.next().submit(() -> {
            log.debug("task1");
        });
        log.debug("task2");

        // 执行定时任务
        eventLoopGroup.next().scheduleWithFixedDelay(() -> {
            log.debug("task3");
        },0, 1, TimeUnit.SECONDS);


    }
}
