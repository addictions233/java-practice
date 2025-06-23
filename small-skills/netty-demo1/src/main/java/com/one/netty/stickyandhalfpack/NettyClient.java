package com.one.netty.stickyandhalfpack;

import com.one.nettybasic.embedded.FixedLengthFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.net.InetSocketAddress;

/**
 * @author one
 * @description TODO
 * @date 2024-6-10
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("localhost", 9876))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            /**
                             * #channelActive会在连接channel建立成功之后,触发active事件
                             */
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                // 客户端分10次发送数据到服务端, 每次发送16个字节
                                for (int i = 0; i < 10; i++) {
                                    ByteBuf byteBuf = ctx.alloc().buffer(16);
                                    byteBuf.writeBytes(new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
                                    ctx.writeAndFlush(byteBuf);
                                    // 使用短连接处理黏包问题
//                                    ctx.channel().close();
                                }
                            }
                        });
                    }
                })
                .connect().sync();
    }
}
