package com.one.springsecuritydemo.handler;

import com.alibaba.fastjson2.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author one
 * @description 自定义认证失败的handler对象  (用于前后端分离)
 * @date 2023-2-18
 */
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {
    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "登录失败:" + exception.getMessage());
        result.put("code", 500);
        String jsonResult = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(jsonResult);
    }
}
