package com.one.reactortest.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * @author one
 * @description 学习使用Netty的基础组件：ByteBuf, ChannelHandler
 * @date 2025-3-2
 */
public class MyNetty {

    @Test
    public void byteBufTest() {
        // 初始化参数：起始容量和最大容量
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(8, 20);

        byteBuf.writeBytes(new byte[]{'a','b','c'});

        // 从ByteBuf中读数据
        System.out.println(byteBuf.isReadable());
        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.readableBytes());

        // 往ByteBuf写数据
        System.out.println(byteBuf.isWritable());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.writableBytes());

        System.out.println(byteBuf.maxCapacity());
        System.out.println(byteBuf.isDirect()); // 是否使用堆外内存，默认是true

        // 默认是池化复用
        ByteBuf upooledByteBuf = UnpooledByteBufAllocator.DEFAULT.buffer(8, 20);
        upooledByteBuf.writeBytes(new byte[]{1,2,3});
    }


    @Test
    public void clientTest() throws InterruptedException {
        // 每个NioEventLoop都有一个selector, 需要使用Selector时直接使用EventLoop就行
        // NioEventLoopGroup是多个EventLoop的集合
        NioEventLoopGroup selector = new NioEventLoopGroup(1);

        // 定义channel
        NioSocketChannel client = new NioSocketChannel();

        // 将channel注册到selector上
        selector.register(client);

        // 定义channelHandler,用来处理服务端的响应数据
        // 响应式编程提前注册处理器Handler，当真的有事件发生时会自动调用Handler的方法来进行处理
        client.pipeline().addLast(new MyInChannelHandler());

        // 由于netty都是响应式的，所以需要同步等待连接完成，否则是异步执行的
        ChannelFuture connect = client.connect(new InetSocketAddress("localhost", 9090));
        ChannelFuture connectFuture = connect.sync();

        // 客户端往服务端发送数据
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello server!".getBytes());
        ChannelFuture sendFuture = client.writeAndFlush(byteBuf);
        sendFuture.sync();

        // 同步等待客户端和服务端关闭连接，否则代码会往下继续执行，即使是建立的长连接
        connectFuture.channel().closeFuture().sync();

        // 关闭连接时才打印
        System.out.println("client over...");

    }
}
