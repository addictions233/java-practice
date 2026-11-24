package com.one.controller;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大都督周瑜
 * 微信ID: dadudu6789
 * 专注帮助程序员提升技术实力，升职涨薪，面试跳槽
 */
@RestController
public class AuthController {

    private static final String key = "1234";

    private static final Map<String, List<String>> urlsToRoles = new HashMap<>();
    private static final Map<Integer, String> uidToRole = new HashMap<>();

    static {
        uidToRole.put(1, "admin");
        uidToRole.put(2, "employee");

        urlsToRoles.put("DELETE|/user", List.of("admin"));
        urlsToRoles.put("POST|/user", List.of("admin"));
        urlsToRoles.put("POST|/addUser", List.of("admin"));
        urlsToRoles.put("GET|/user", List.of("admin", "employee"));
    }

    @GetMapping("/validateToken")
    public Integer validateToken(@RequestHeader String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        return ((NumberWithFormat)jwt.getPayload("uid")).intValue();
    }

    @GetMapping("/login")
    public String login(String username, String password) {

        // 验证用户名和密码
        // ...

        // 登录成功
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("uid", username.equals("admin") ? 1 : 2);
        map.put("role", username.equals("admin") ? "admin" : "employee");

        return JWTUtil.createToken(map, key.getBytes());
    }

    @PostMapping("/validateUrl")
    public String validateUrl(@RequestBody Map<String, Object> validateInfo) {
        Integer uid = (Integer) validateInfo.get("uid");
        String url = (String) validateInfo.get("url");

        String role = uidToRole.get(uid);

        if (urlsToRoles.get(url).contains(role)) {
            return "success";
        } else {
            throw new RuntimeException("没有权限");
        }
    }
}
