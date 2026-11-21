package com.one.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 使用 webflux 来处理 http 请求
 * @author one
 */
@RestController
public class MyController {

    @GetMapping("/test")
    public String test() {
        return "hello webmvc";
    }

    @GetMapping("/testFlux")
    public Flux<String> testFlux() {
        return Flux.just("hello webflux");
    }

    @GetMapping("/testMono")
    public Mono<String> testMono() {
        return Mono.just("hello mono");
    }

    @GetMapping(value = "/testSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> testSSE() {
        return Flux.just("hello flux1", "hello flux2").delayElements(Duration.ofSeconds(1));
    }
}
