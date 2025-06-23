package com.one.core;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义授权失败的处理器,没有权限, 避免抛出AccessDeniedException异常
 * @author one
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 认证和授权不通过, 返回403, 非法请求
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        // 403
        response.setStatus(HttpStatus.FORBIDDEN.value());
        PrintWriter out = response.getWriter();
        out.print("非法请求");
        out.flush();
        out.close();
    }
}
