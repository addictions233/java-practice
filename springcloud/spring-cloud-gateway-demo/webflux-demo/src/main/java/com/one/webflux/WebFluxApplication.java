package com.one.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * 启动类: 和普通的SpringBoot工程一样
 */
@SpringBootApplication
@EnableR2dbcRepositories
public class WebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class);
    }
}
