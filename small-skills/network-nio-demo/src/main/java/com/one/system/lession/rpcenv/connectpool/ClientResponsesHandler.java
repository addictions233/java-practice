package com.one.system.lession.rpcenv.connectpool;

import com.one.system.lession.rpcenv.PackageMsg;
import com.one.system.lession.packmode.MsgCallBackMapping;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: 马士兵教育
 * @create: 2020-07-18 11:30
 */
public class ClientResponsesHandler extends ChannelInboundHandlerAdapter {

    //consumer.....
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackageMsg pkg = (PackageMsg) msg;
        MsgCallBackMapping.runCallBack(pkg);
    }
}
