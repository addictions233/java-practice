package com.one.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author one
 * @description TODO
 * @date 2024-4-2
 */
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        // 1.启动类
        new Bootstrap()
                // 2.添加EventLoop
                .group(new NioEventLoopGroup())
                // 3.选择客户段channel实现
                .channel(NioSocketChannel.class)
                // 4.添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    // 5. 在连接建立后被调用
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 6.连接到服务器
                .connect(new InetSocketAddress("localhost", 8080))
                .sync()  // 阻塞方法, 直到建立连接
                .channel()  // 获取连接对象
                // 7.向服务器发送数据
                .writeAndFlush("hello netty");
    }
}
