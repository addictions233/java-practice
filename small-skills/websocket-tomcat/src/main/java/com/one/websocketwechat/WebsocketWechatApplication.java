package com.one.websocketwechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author one
 * ‌角色定位不同‌
 *
 * Tomcat WebSocket‌：作为 Servlet 容器，负责‌底层协议解析、连接管理及帧传输‌。它实现了 Java WebSocket API (JSR-356)，直接处理 TCP 连接升级和数据帧读写，不关心具体业务逻辑。
 * Spring WebSocket‌：属于应用层框架，负责‌消息路由、会话管理、协议转换及安全拦截‌。它封装了底层细节，提供 WebSocketHandler、STOMP 消息代理等高级抽象，便于与 Spring MVC、Security 等模块协同工作。
 * ‌开发模式与注解体系‌
 *
 * Tomcat 方式‌：使用标准 JSR-356 注解（如 @ServerEndpoint、@OnOpen）。代码直接操作 Session 对象，需手动管理连接状态，‌无法直接注入 Spring Bean‌（需通过特殊配置器 workaround）。
 * Spring 方式‌：使用 Spring 特有注解（如 @EnableWebSocket、@MessageMapping）。支持依赖注入（DI），处理器类可自动注入 Service/Repository，且支持‌编程式注册‌（WebSocketConfigurer），灵活性更高。
 * ‌功能扩展性‌
 *
 * Tomcat‌：功能基础，仅支持原生 WebSocket 协议。若需 STOMP 子协议或跨浏览器兼容（SockJS），需自行集成第三方库。
 * Spring‌：内置支持 STOMP 协议‌（简单面向文本的消息协议）和 SockJS 降级方案，自动处理心跳、断线重连及旧浏览器兼容，适合复杂实时场景（如聊天室、推送）。
 * ‌安全与拦截机制‌
 *
 * Tomcat‌：握手阶段认证较繁琐，通常需在 @OnOpen 中手动校验 Token 或 Session，缺乏统一拦截器。
 * Spring‌：提供 HandshakeInterceptor 接口，可在握手前统一拦截请求，轻松集成 Spring Security 进行身份认证与授权，实现声明式安全控制。‌
 */
@SpringBootApplication
public class WebsocketWechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketWechatApplication.class, args);
	}

}
