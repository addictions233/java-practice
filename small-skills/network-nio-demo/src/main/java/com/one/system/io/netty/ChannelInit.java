package com.one.system.io.netty;

import io.netty.channel.*;

//为啥要有一个inithandler，可以没有，但是MyInHandler就得设计成单例
@ChannelHandler.Sharable
class ChannelInit extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        ChannelPipeline p = client.pipeline();
        p.addLast(new MyInHandler());//2,client::pipeline[ChannelInit,MyInHandler]
        ctx.pipeline().remove(this);
        //3,client::pipeline[MyInHandler]
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("haha");
//        super.channelRead(ctx, msg);
//    }
}
