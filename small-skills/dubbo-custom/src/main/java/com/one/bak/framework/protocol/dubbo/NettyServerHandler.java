package com.one.bak.framework.protocol.dubbo;

import com.one.bak.framework.Invocation;
import com.one.bak.framework.register.LocalRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;

        Class serviceImpl = LocalRegister.get(invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        Object result = method.invoke(serviceImpl.getDeclaredConstructor().newInstance(), invocation.getParameterValues());

        System.out.println("Netty-------------" + result.toString());
        ctx.writeAndFlush("Netty:" + result);
    }
}
