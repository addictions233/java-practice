package com.one.reactortest;

/**
 * @author one
 * @description 使用NIO模拟Netty的Reactor模型 (手写Netty的Reactor模型)
 * @date 2025-1-14
 */
public class MainThread {

    /**
     * 主线程需要执行的任务
     */
    public static void main(String[] args) {
        // 这里不做关于IO 和 业务的事情

        // 1. 创建IO Thread (一个或者多个)
        SelectThreadGroup bossGroup = new SelectThreadGroup(1);

        SelectThreadGroup workerGroup = new SelectThreadGroup(3);
        // 把监听的9999的server注册到某一个Selector上
        bossGroup.setWorker(workerGroup);

        bossGroup.bind(9090);
    }
}
