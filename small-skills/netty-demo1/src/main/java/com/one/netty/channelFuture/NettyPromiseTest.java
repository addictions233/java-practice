package com.one.netty.channelFuture;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author one
 * @description TODO
 * @date 2024-4-6
 */
@Slf4j
public class NettyPromiseTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1,准备EventLoop
        EventLoop eventLoop = new NioEventLoopGroup().next();

        // 2,创建Promise对象
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        // 异步执行任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.debug("执行任务...");
//                    int i = 1 / 0;
                    promise.setSuccess(50);
                } catch (Exception e) {
                    promise.setFailure(e);
                }
            }
        }).start();

        log.debug("等待任务执行结束...");
        log.debug("执行结果:" + promise.get());
    }
}
