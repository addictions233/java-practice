package com.one.reactortest.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

/**
 * @author one
 * @description EventLoopGroup是一组EventLoop, 而EventLoop既包含了一个线程Thread, 也封装了一个多路复用器Selector
 * @date 2025-1-19
 */
public class EventLoopGroupTest {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup selector = new NioEventLoopGroup(2);

//        // 测试EventLoopGroup包含线程
//        eventLoopGroup.execute(() -> {
//            for(;;) {
//                System.out.println(Thread.currentThread().getName() + "print 0");
//            }
//        });
//
//        eventLoopGroup.execute(() -> {
//            for(;;) {
//                System.out.println(Thread.currentThread().getName() + "print 1");
//            }
//        });

        // EventLoopGroup是EventLoop的集合组
        EventLoop eventLoop = selector.next();

        // 构建用户客户端的Channel
        NioSocketChannel socketChannel = new NioSocketChannel();


        // 将自定义的ChannelHandler添加到ChannelPipeline中, 用来处理服务端发送的数据
        socketChannel.pipeline().addLast(new MyInChannelHandler());

        // 将channel注册到Selector
        selector.register(socketChannel);  // epoll_ctl(5,ADD,3)

        // 客户端是调用connect()连接函数, 进行三次握手
        ChannelFuture connectFuture = socketChannel.connect(new InetSocketAddress("localhost", 9090));
        // 默认都是异步的, 调用sync(), 同步等待连接建立成功
        connectFuture.sync();

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello server".getBytes(StandardCharsets.UTF_8));
        ChannelFuture sendFuture = socketChannel.writeAndFlush(byteBuf);
        // 默认的发送异步的, 调用sync(), 同步等待发送结果
        sendFuture.sync();

        // 这个方法会一直同步阻塞, 直到服务端主动关闭连接,然后往下执行
        connectFuture.channel().closeFuture().sync();
        System.out.println("client over....");
    }
}
