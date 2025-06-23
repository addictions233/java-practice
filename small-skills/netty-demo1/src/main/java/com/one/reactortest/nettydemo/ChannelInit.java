package com.one.reactortest.nettydemo;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author one
 * @description 提供给用户使用, 专门用来注册用户自定义的ChannelHandler, 模拟Netty提供的ChannelInitializer
 * @date 2025-1-19
 */
@ChannelHandler.Sharable // 它的唯一作用就是将用户自定义的ChannelHandler添加到ChannelPipeline, 所以它必须是支持Channel共享的
public abstract class ChannelInit extends ChannelInboundHandlerAdapter{

    /**
     * 在channel注册时，会channelRegistered()方法，然后会调用initChannel()方法，将用户自定义的ChannelHandler添加到ChannelPipeline中
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        this.initChannel(ctx);
    }

    /**
     * 提供给用户重新的方法， 用来将用户自定义的ChannelHandler添加到ChannelPipeline中， 模拟Netty提供的ChannelInitializer
     * 这样既能保证ChannelHandler在不同的Channel中共享使用，又能保证ChannelHandler在不同Channel中初始化不同的ChannelHandler，独立处理数据
     * @param ctx
     */
    protected abstract void initChannel(ChannelHandlerContext ctx);

}
