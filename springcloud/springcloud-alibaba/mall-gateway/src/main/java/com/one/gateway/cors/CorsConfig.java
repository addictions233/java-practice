package com.one.gateway.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @description: 基于配置类解决跨域访问
 * 跨域问题的产生: 跨域问题源于浏览器的同源策略。同源策略要求来自同一源的资源之间可以相互访问，而不同源的资源则受到限制。
 * 这意味在实际应用中前端页面、后端服务、第三方API等需要相互通信的资源，如果不在同一域下，就会遇到跨域问题。
 *
 * 同源策略（Same Orgin Policy）: 是一种约定，它是浏览器核心也最基本的安全功能，
 * 它会阻止一个域的js脚本和另外一个域的内容进行交互，如果缺少了同源策略，浏览器很容易受到XSS、CSRF等攻击。
 * 所谓同源（即在同一个域）就是两个页面具有相同的协议（protocol）、主机（host）和端口号（port）。
 * @author: wanjunjie
 * @date: 2024/11/29
 */
//@Configuration   // gateway网关解决跨域有两个方式: 一种是在配置文件中直接配置, 一种是基于配置类
public class CorsConfig {

    /**
     * 实施CORS策略
     * CORS（跨来源资源共享）是一种W3C标准，它定义了一种浏览器和服务器交互的方式，以确定是否允许跨源请求。
     * 当浏览器发出跨域请求时，它会发送一个包含预检请求的OPTIONS请求。
     * OPTIONS请求是一种HTTP请求方法，用于获取目标资源支持的通信选项。
     * 服务器在接收到OPTIONS请求后，会返回一个包含CORS头部信息的响应。
     * 这些头部信息告诉浏览器是否允许跨域请求，以及允许哪些请求方法和携带哪些头信息。
     *
     * 为了解决跨域问题，Gateway网关需要配置允许OPTIONS请求。
     * 当浏览器发出跨域请求时，它会先发送一个OPTIONS请求，以了解目标资源是否允许跨域访问。
     * 如果目标资源允许跨域访问，浏览器才会发出实际的GET、POST等请求。因此，允许OPTIONS请求是解决跨域问题的关键步骤。
     * 通过实施CORS策略，Gateway网关可以控制哪些源可以访问后端服务，以及允许哪些HTTP方法和头信息。这有助于确保应用的安全性和稳定性。
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
