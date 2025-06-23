package com.one.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelGroupFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author one
 * @description TODO
 * @date 2024-4-6
 */
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        // 带有future, promise的都是和异步方法一起使用的, 用来处理结果
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("localhost", 8080));
        // 2.1使用sync方法同步处理结果, 建立连接会阻塞线程
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        channel.writeAndFlush("hello");

        // 2.2使用addListener方法异步处理结果
//        channelFuture.addListener(new ChannelGroupFutureListener() {
//            // 在nio线程建立好了之后,会调用该方法
//            // 是由nio线程调用,而不是主线程调用
//            @Override
//            public void operationComplete(ChannelGroupFuture future) throws Exception {
//                Channel channel = channelFuture.channel();
//                channel.writeAndFlush("hello");
//            }
//        });

        // 获取CloseFuture对象, 1.同步处理关闭, 2.异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
        log.debug("等待关闭...");
        closeFuture.sync();
        log.debug("处理关闭之后的操作");

        group.shutdownGracefully();

//        closeFuture.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                log.debug("处理关闭之后的操作");
//            }
//        });

    }
}
