package com.one.reactortest.nettydemo;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;


/**
 * @author one
 * @description 服务端处理客户端accept()连接事件的ChannelHandler, 只存在于Server端
 * @date 2025-1-19
 */
public class MyAcceptChannelHandler extends ChannelInboundHandlerAdapter {

    private EventLoopGroup eventLoopGroup;


    private ChannelHandler channelHandler;

    public MyAcceptChannelHandler(EventLoopGroup eventLoopGroup, ChannelHandler channelHandler) {
        this.eventLoopGroup = eventLoopGroup;
        this.channelHandler = channelHandler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server registered...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 由于服务端Server处理的是Accept连接事件,  msg对应的应该是客户端连接的Socket 即SocketChannel
        // Netty底层自动帮我Accept了新的连接, 我们只用拿到新生成的SocketChannel进行注册Register就可以
        SocketChannel socketChannel = (SocketChannel) msg;

        // 对客户端的连接Channel中添加处理响应的ChannelHandler, 这里共用MyInChannelHandler
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(this.channelHandler);

        // 注册新创建的Channel
        this.eventLoopGroup.register(socketChannel);
    }
}
