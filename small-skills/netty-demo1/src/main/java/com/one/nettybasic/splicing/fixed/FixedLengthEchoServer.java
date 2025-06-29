package com.one.nettybasic.splicing.fixed;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import java.net.InetSocketAddress;

/**
 * 作者：Mark
 * 类说明：
 */
public class FixedLengthEchoServer {

    public static final String RESPONSE = "Welcome to Netty!";
    public static final int PORT = 9996;

    public static void main(String[] args) throws InterruptedException {
        FixedLengthEchoServer fixedLengthEchoServer = new FixedLengthEchoServer();
        System.out.println("服务器即将启动");
        fixedLengthEchoServer.start();
    }

    public void start() throws InterruptedException {
        final FixedLengthServerHandler serverHandler = new FixedLengthServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            ServerBootstrap b = new ServerBootstrap();/*服务端启动必须*/
            b.group(group)/*将线程组传入*/
                .channel(NioServerSocketChannel.class)/*指定使用NIO进行网络传输*/
                .localAddress(new InetSocketAddress(PORT))/*指定服务器监听端口*/
                /*服务端每接收到一个连接请求，就会新启一个socket通信，也就是channel，
                所以下面这段代码的作用就是为这个子channel增加handle*/
                .childHandler(new ChannelInitializerImp());
            ChannelFuture f = b.bind().sync();/*异步绑定到服务器，sync()会阻塞直到完成*/
            System.out.println("服务器启动完成，等待客户端的连接和数据.....");
            f.channel().closeFuture().sync();/*阻塞直到服务器的channel关闭*/
        } finally {
            group.shutdownGracefully().sync();/*优雅关闭线程组*/
        }
    }

    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(
                    new FixedLengthFrameDecoder(
                            FixedLengthEchoClient.REQUEST.length()));
            ch.pipeline().addLast(new FixedLengthServerHandler());
        }
    }

}
