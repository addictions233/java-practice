package com.one.controller;

import com.one.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author one
 * @description 测试controller
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name) {
//        return orderService.sayHello(name);
        orderService.sayHelloStream(name);
        return name;
    }

}
