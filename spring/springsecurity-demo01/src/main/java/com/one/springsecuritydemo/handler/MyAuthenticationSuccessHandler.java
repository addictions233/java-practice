package com.one.springsecuritydemo.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author one
 * @description 自定义登录认证成功的处理器对象, (用于前后端分离)
 * @date 2023-2-4
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "登录成功");
        result.put("status", 200);
        // authentication对象中封装了认证之后的用户信息
        result.put("authentication", authentication);
        String jsonResult = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResult);
    }
}
