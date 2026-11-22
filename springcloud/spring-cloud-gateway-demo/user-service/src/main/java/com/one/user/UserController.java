package com.one.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大都督周瑜
 * 微信ID: dadudu6789
 * 专注帮助程序员提升技术实力，升职涨薪，面试跳槽
 */
@RestController
public class UserController {

    public static List<Map<String, String>> USER_INFO = new ArrayList<>();

    @PostMapping("/addUser")
    public String addUser(@RequestBody Map<String, String> userInfo) {
        USER_INFO.add(0, userInfo);

        return "success";
    }
}
