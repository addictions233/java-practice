package com.one.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

/**
 * @author one
 * @description TODO
 * @date 2024-4-4
 */
public class EventLoopServer {
    public static void main(String[] args) {
        // 创建一个独立的EventLoopGroup
        EventLoopGroup group = new DefaultEventLoop();
        new ServerBootstrap()
                // 区分boss和work两个EventLoopGroup
                // boss只负责ServerSocketChannel上的accept事件
                // worker只负责socketChannel上的读写事件
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                // 打印上一步转换好的字符串
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(byteBuf.toString(Charset.defaultCharset()));
                                // 将消息传递给下一个Handler
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast(group,"handler2", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                // 打印上一步转换好的字符串
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(byteBuf.toString(Charset.defaultCharset()));
                                // 将消息传递给下一个Handler
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
