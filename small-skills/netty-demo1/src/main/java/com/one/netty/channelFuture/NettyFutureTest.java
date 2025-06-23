package com.one.netty.channelFuture;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author one
 * @description 测试netty中的Future, netty是异步通讯框架, 使用ChannelFuture获取异步结果
 * @date 2024-4-6
 */
@Slf4j
public class NettyFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Future<Integer> future = group.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("正在进行计算...");
                Thread.sleep(1000);
                return 50;
            }
        });

        log.debug("等待执行结果...");
        log.debug("执行结果:" + future.get());
        // getNow方法如果异步现场执行完毕返回执行结果
        // 如果异步线程没有执行完毕,返回null
        log.debug("执行结果:" + future.getNow());

//        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
//            @Override
//            public void operationComplete(Future<? super Integer> future) throws Exception {
//                log.debug("接收结果:{}", future.getNow());
//            }
//        });




    }
}
