package com.one.netty.stickyandhalfpack;

import com.one.nettybasic.embedded.FixedLengthFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author one
 * @description 测试黏包和半包现象
 * @date 2024-6-10
 */
@Slf4j
public class NettyServer {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_RCVBUF, 10)   // 调整服务器端的接收缓冲区的大小,这样服务端缓存区较小,会出现半包现象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 使用定长解码器解决黏包半包现象,参数:一个数据包的长度, 八个字节为一个包
//                            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(8));
//                            // 使用行分割符解码器解决黏包半包现象, 参数是最大长度,如果到了最大长度还没出现行分隔符就会报错
//                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                            // 使用长度字段解码器解决黏包半包现象, 最大长度，长度偏移，长度占用字节，长度调整，剥离字节数
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 1, 0, 1));
                            // 先处理黏包半包,再打印日志,这样才能看到处理之后的结果
                            socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(10016).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
