package com.one.reactortest.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * @author one
 * @description 处理网络读写数据的ChannelHandler
 * @date 2025-1-19
 */
//@ChannelHandler.Sharable  // 用户只用自己实现ChannelHandle，至于能否在多个Channel中共享使用, 不做限制
    // 如果我们既希望将ChannelHandler添加到ChannelPipeline中，又希望它是共享的，那么就需要使用@Sharable注解
    // 如果我们既希望将ChannelHandler添加到ChannelPipeline中，又不希望它是每个连接独占的，那么就不需要使用@Sharable注解，而是使用ChannelInitializer
public class MyInChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client  registered...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client  active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Netty底层会帮利用channel.read()读取数据, 然后将数据封装成ByteBuf对象，然后传递给ChannelHandler
        // 这里的msg就是ByteBuf对象
        ByteBuf byteBuf  = (ByteBuf) msg;
        // 第一种写法：是读取ByteBuf中的数据，会移动读指针
//        CharSequence charSequence = byteBuf.readCharSequence(byteBuf.readableBytes(), CharsetUtil.UTF_8);
        // 第二种写法：是获取ByteBuf中的数据，不会移动读指针， 这样就可以重复获取
        CharSequence str = byteBuf.getCharSequence(0, byteBuf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println("receive from server:" + str);
        // 将服务端发送的内容写到服务端
        ctx.writeAndFlush(byteBuf);
    }
}
