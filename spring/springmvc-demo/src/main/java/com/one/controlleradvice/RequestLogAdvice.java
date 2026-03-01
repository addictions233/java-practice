package com.one.controlleradvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * 全局请求日志记录
 * 实现 ResponseBodyAdvice 拦截响应体
 */
@ControllerAdvice(basePackages = "com.one.controller")
public class RequestLogAdvice implements ResponseBodyAdvice<Object> {

    // 请求开始时间存储键
    private static final String START_TIME_KEY = "REQUEST_START_TIME";
    private static final String TRACE_ID_KEY = "TRACE_ID";

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 判断是否支持拦截（这里拦截所有响应）
     */
    @Override
    public boolean supports(MethodParameter returnType, 
                          Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 在响应体写入前执行：记录完整日志
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        
        // 获取原始请求
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest)request;
            
            // 计算执行时间
            Long startTime = (Long) req.getAttribute(START_TIME_KEY);
            long duration = startTime != null ? 
                ChronoUnit.MILLIS.between(
                    java.time.Instant.ofEpochMilli(startTime), 
                    java.time.Instant.now()
                ) : 0;
            
            // 构建日志信息
            String traceId = (String) req.getAttribute(TRACE_ID_KEY);
            String method = req.getMethod();
            String uri = req.getRequestURI();
            String queryString = req.getQueryString();
            String clientIp = getClientIp(req);
            String userAgent = req.getHeader("User-Agent");
            
            // 记录响应日志
//            log.info("""
//
//                ═══════════════════════════════════════════
//                【响应日志】TraceID: {}
//                接口: {} {}
//                耗时: {} ms
//                状态码: {}
//                响应体: {}
//                ═══════════════════════════════════════════
//                """,
//                traceId,
//                method,
//                uri + (queryString != null ? "?" + queryString : ""),
//                duration,
//                response.getStatusCode().value(),
//                formatBody(body)
//            );
            
            // 清理 ThreadLocal（如果使用的话）
            // MDC.remove("traceId");
        }
        
        return body;
    }

    /**
     * 记录请求日志（需要在拦截器或 Filter 中调用）
     */
    public static void logRequest(HttpServletRequest request, Object body) {
        String traceId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        request.setAttribute(TRACE_ID_KEY, traceId);
        request.setAttribute(START_TIME_KEY, System.currentTimeMillis());
        
        // 可以放入 MDC 供日志框架使用
        // MDC.put("traceId", traceId);
        
//        log.info("""
//
//            ═══════════════════════════════════════════
//            【请求日志】TraceID: {}
//            时间: {}
//            客户端: {} ({})
//            接口: {} {}
//            Content-Type: {}
//            请求体: {}
//            ═══════════════════════════════════════════
//            """,
//            traceId,
//            LocalDateTime.now(),
//            getClientIp(request),
//            request.getHeader("User-Agent"),
//            request.getMethod(),
//            request.getRequestURI(),
//            request.getContentType(),
//            body != null ? body.toString() : "无"
//        );
    }

    /**
     * 获取客户端真实 IP
     */
    private static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个代理情况，取第一个 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    /**
     * 格式化响应体（截断过长的内容）
     */
    private String formatBody(Object body) {
        if (body == null) return "null";
        try {
            String json = objectMapper.writeValueAsString(body);
            // 截断过长的响应（超过 1000 字符）
            if (json.length() > 1000) {
                return json.substring(0, 1000) + "... [截断，总长度: " + json.length() + "]";
            }
            return json;
        } catch (Exception e) {
            return body.toString();
        }
    }
}