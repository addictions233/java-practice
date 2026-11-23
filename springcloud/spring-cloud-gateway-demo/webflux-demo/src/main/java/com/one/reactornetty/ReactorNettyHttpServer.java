package com.one.reactornetty;

import reactor.core.publisher.Flux;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

public class ReactorNettyHttpServer {
    public static void main(String[] args) {
        HttpServer httpServer = HttpServer.create();
        DisposableServer server = httpServer.host("127.0.0.1")
                .port(8080)
                .handle((request, response) -> {
                    Flux<String> result = request.receive()
                            .asString()
                            .doOnNext(System.out::println)
                            .map(data -> "receive data:" + data);
                    return response.sendString(result);
                })
                .bindNow();

        server.onDispose().block();
    }
}
