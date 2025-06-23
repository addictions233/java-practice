package com.one.system.io.netty;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

class  MyAcceptHandler  extends ChannelInboundHandlerAdapter {


    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup thread, ChannelHandler myInHandler) {
        this.selector = thread;
        this.handler = myInHandler;  //ChannelInit
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server registerd...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //  listen  socket   accept    client
        //  socket           R/W
        SocketChannel client = (SocketChannel) msg;  //accept  我怎么没调用额？
        //2，响应式的  handler
        ChannelPipeline p = client.pipeline();
        p.addLast(handler);  //1,client::pipeline[ChannelInit,]

        //1，注册
        selector.register(client);


    }
}
