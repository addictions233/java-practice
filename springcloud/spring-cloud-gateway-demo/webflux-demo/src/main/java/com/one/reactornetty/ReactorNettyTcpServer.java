package com.one.reactornetty;

import reactor.core.publisher.Flux;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

public class ReactorNettyTcpServer {

    public static void main(String[] args) {
        // 建立 TCP 服务端
        DisposableServer server = TcpServer.create()
                .handle((inbound, outbound) -> {
                    // 接收数据
                    return inbound.receive()
                            .asString()
                            .doOnNext(System.out::println).then();
                })
                .host("127.0.0.1")
                .port(8090)
                .bindNow();

        // 服务端阻塞
        server.onDispose().block();
    }
}
