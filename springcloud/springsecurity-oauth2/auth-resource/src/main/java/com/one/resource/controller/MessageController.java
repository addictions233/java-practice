package com.one.resource.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: controller
 * @author: wanjunjie
 * @date: 2024/11/29
 */
@RestController
public class MessageController {

    @GetMapping("/messages1")
    public String getMessages1() {
        return " hello Message 1";
    }

    @GetMapping("/messages2")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public String getMessages2() {
        return " hello Message 2";
    }

    @GetMapping("/messages3")
    @PreAuthorize("hasAuthority('SCOPE_Message')")
    public String getMessages3() {
        return " hello Message 3";
    }
}
