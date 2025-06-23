package com.one.bak.framework.protocol.http;

import com.one.bak.framework.Invocation;
import com.one.bak.framework.URL;
import com.one.bak.framework.protocol.Protocol;

/**
 * @description: 使用http进行远程调用的实现
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class HttpProtocol implements Protocol {
    /**
     * Provider开启http服务
     */
    @Override
    public void start(URL url) {
        new HttpServer().start(url.getHostName(), url.getPort());
    }

    /**
     * Consumer进行http服务远程调用
     */
    @Override
    public Object send(URL url, Invocation invocation) {
        return new HttpClient().send(url.getHostName(), url.getPort(), invocation);
    }
}
