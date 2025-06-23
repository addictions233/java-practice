package com.one.configdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fox
 */
@RestController
@RefreshScope
public class IndexController {

    @Value("${common.age}")
    private String age;
    @Value("${common.name}")
    private String name;

    @GetMapping("/index")
    public String hello() {
        return name+","+age;
    }

}
