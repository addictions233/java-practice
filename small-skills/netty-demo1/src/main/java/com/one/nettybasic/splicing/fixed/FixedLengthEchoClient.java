package com.one.nettybasic.splicing.fixed;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import java.net.InetSocketAddress;

/**
 * 作者：Mark
 * 类说明：
 */
public class FixedLengthEchoClient {

    public final static String REQUEST = "Mark,zhuge,zhouyu,fox,loulan";

    private final String host;

    public FixedLengthEchoClient(String host) {
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            final Bootstrap b = new Bootstrap();;/*客户端启动必须*/
            b.group(group)/*将线程组传入*/
                    .channel(NioSocketChannel.class)/*指定使用NIO进行网络传输*/
                    .remoteAddress(new InetSocketAddress(host,FixedLengthEchoServer.PORT))/*配置要连接服务器的ip地址和端口*/
                    .handler(new ChannelInitializerImp());
            ChannelFuture f = b.connect().sync();
            System.out.println("已连接到服务器.....");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(
                    new FixedLengthFrameDecoder(
                            FixedLengthEchoServer.RESPONSE.length()));
            ch.pipeline().addLast(new FixedLengthClientHandler());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new FixedLengthEchoClient("127.0.0.1").start();
    }
}
