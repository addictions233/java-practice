package com.one.reactortest.nettydemo;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * @author one
 * @description Netty 服务端简单写法
 * @date 2025-1-19
 */
public class ServerMode {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        // 构建Channel
        NioServerSocketChannel serverSocketChannel = new NioServerSocketChannel();

        // 添加ChannelHandler
        ChannelPipeline pipeline = serverSocketChannel.pipeline();
        pipeline.addLast(new MyAcceptChannelHandler(eventLoopGroup, new ChannelInit() {
            @Override
            protected void initChannel(ChannelHandlerContext ctx) {
                ctx.pipeline().addLast(new MyInChannelHandler());
                ctx.pipeline().remove(this);
            }
        }));

        // 将Channel注册到Selector上
        eventLoopGroup.register(serverSocketChannel);

        ChannelFuture bindFuture = serverSocketChannel.bind(new InetSocketAddress("localhost", 9090));
        bindFuture.sync();

        bindFuture.channel().closeFuture().sync();
        System.out.println("server close....");
    }
}
