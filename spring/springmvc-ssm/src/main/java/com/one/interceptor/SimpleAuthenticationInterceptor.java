package com.one.interceptor;

import com.alibaba.fastjson2.JSON;
import com.one.domain.UserDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * @author one
 * @description 使用mvc中的拦截器手动实现一个简单的认证和授权,而spring security使用的是FilterChain过滤器链
 * @date 2022-9-18
 */
@Component
public class SimpleAuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDetail userDetail = (UserDetail) request.getSession().getAttribute(UserDetail.SESSION_USER_KEY);
        String requestURI = request.getRequestURI();
        if (Objects.isNull(userDetail)) {
           writeContent(response, "没有登录,无法访问");
           return false;
        }
        Set<String> authorities = userDetail.getAuthorities();
        String substring = requestURI.substring(requestURI.lastIndexOf("/"));
        if (!authorities.contains(substring)) {
            writeContent(response, "你没有权限访问该资源");
            return true;
        }
        return true;
    }

    private void writeContent(HttpServletResponse response, String content) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Springboot中使用response响应前端结果时,必须转换为json,否则会乱码
//        PrintWriter writer = response.getWriter();
//        writer.write(JSON.toJSONString(content));
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSON.toJSONBytes(content));
        outputStream.flush();
        outputStream.close();;
    }
}
