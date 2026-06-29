package com.one.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 在 Spring Boot 中，底层默认仍由 Tomcat（或 Undertow/Jetty）提供 WebSocket 实现，
 * Spring 只是在其之上构建了更易用的 API。
 * 因此，“基于 Spring”通常指使用 spring-websocket 模块，而非替代 Tomcat 的底层能力
 */
@SpringBootApplication
public class WebsocketStompApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketStompApplication.class, args);
	}

}
