package com.one.reactornetty;

import reactor.core.publisher.Flux;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

public class ReactorNettyTcpClient {

    public static void main(String[] args) {
        // 创建 Tcp 客户端
        Connection connection = TcpClient.create()
                .host("127.0.0.1")
                .port(8090)
                .connectNow();

        // 使用客户端发送数据
        connection.outbound()
                .sendString(Flux.just("hello tcp server"))
                .then()
                .subscribe();

        // 客户端阻塞
        connection.onDispose().block();

    }
}
