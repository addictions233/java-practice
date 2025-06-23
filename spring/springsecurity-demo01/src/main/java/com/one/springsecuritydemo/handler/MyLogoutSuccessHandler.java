package com.one.springsecuritydemo.handler;

import com.alibaba.fastjson2.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author one
 * @description 自定义注销登录成功的处理handler对象  (用于前后端分离)
 * @date 2023-2-19
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "注销成功");
        result.put("code", 200);
        result.put("authentication", authentication);
        String jsonResult = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResult);
    }
}
