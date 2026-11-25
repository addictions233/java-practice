package com.one.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/test")
    public String test() {
        return "hello webmvc";
    }

    /**
     * Flux代表0到N个元素的异步序列，类似于传统编程中的集合或流
     * @return Flux
     */
    @GetMapping("/testFlux")
    public Flux<String> testFlux() {
        return Flux.just("hello webflux");
    }

    /**
     * Mono则专为0或1个元素的场景设计，类似于Java中的Optional或Future
     * @return Mono
     */
    @GetMapping("/testMono")
    public Mono<String> testMono() {
        return Mono.just("hello mono");
    }

    @GetMapping(value = "/testSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> testSSE() {
        return Flux.just("hello flux1", "hello flux2").delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/findStudent/{id}")
    public Mono<Student> findAll(@PathVariable("id") Integer id) {
        return studentRepository.findById(id);
    }
}
