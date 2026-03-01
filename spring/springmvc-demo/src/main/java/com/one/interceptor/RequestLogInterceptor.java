package com.one.interceptor;

import com.one.controlleradvice.RequestLogAdvice;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * 请求日志拦截器 - 在请求进入 Controller 前记录
 */
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        
        // 读取请求体（如果是 POST/PUT）
        Object requestBody = null;
        if (request.getContentType() != null && 
            request.getContentType().contains("application/json")) {
            
            // 使用包装器读取请求体（见下文）
            if (request instanceof CachedBodyHttpServletRequest) {
                String body = ((CachedBodyHttpServletRequest) request).getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));
                requestBody = body;
            }
        }
        
        // 记录请求日志
        RequestLogAdvice.logRequest(request, requestBody);
        
        return true;
    }
}