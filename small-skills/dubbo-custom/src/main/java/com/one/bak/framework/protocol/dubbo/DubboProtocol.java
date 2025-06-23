package com.one.bak.framework.protocol.dubbo;

import com.one.bak.framework.Invocation;
import com.one.bak.framework.URL;
import com.one.bak.framework.protocol.Protocol;

public class DubboProtocol implements Protocol {

    @Override
    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostName(), url.getPort());

    }

    @Override
    public String send(URL url, Invocation invocation) {
        NettyClient nettyClient = new NettyClient();
        return nettyClient.send(url.getHostName(),url.getPort(), invocation);
    }
}
