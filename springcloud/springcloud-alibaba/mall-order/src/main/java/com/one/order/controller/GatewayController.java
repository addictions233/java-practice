package com.one.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试网关对请求头的处理
 * @author: wanjunjie
 * @date: 2024/11/29
 */
@RestController
@Slf4j
public class GatewayController {

    /**
     * 测试在网关中添加请求头
     */
    @GetMapping("/testGateway")
    public String testGateway(HttpServletRequest request) {
        log.info("gateway获取请求头X-Request-color:" + request.getHeader("X-Request-color"));
        return "success";
    }

    @GetMapping("/testGateway2")
    public String testGateway2(@RequestHeader("X-Request-color") String color) {
        log.info("gateway获取请求头X-Request-color:" + color);
        return "success";
    }

    /**
     * 测试在网关中添加请求参数
     */
    @GetMapping("/testgateway3")
    public String testGateway3(@RequestParam("color") String color) {
        log.info("gateWay获取请求参数color:"+color);
        return "success";
    }
}
