package com.one.reactornetty;


import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

public class ReactorNettyHttpClient {

    public static void main(String[] args) {
        HttpClient client = HttpClient.create();
        String result = client.post()
                .uri("http://127.0.0.1:8080/test")
                .send(ByteBufFlux.fromString(Mono.just("hello server")))
                .responseContent()
                .asString()
                .next()
                .block();

        System.out.println(result);
    }
}
