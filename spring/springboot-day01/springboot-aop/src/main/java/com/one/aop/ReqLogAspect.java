package com.one.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * 日志切面，用于记录接口请求和响应信息
 */
@Slf4j
@Aspect
@Component
public class ReqLogAspect {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 定义切点，匹配fact-service模块下所有controller包中的方法
     */
    @Pointcut(value = "execution(* com.one.*.controller.*.*(..))")
    private void webPointcut() {
    }

    /**
     * 环绕通知，用于记录接口请求和响应信息
     *
     * @param joinPoint 切入点对象，包含被拦截方法的信息
     * @return 响应结果
     * @throws Throwable 如果执行过程中发生异常
     */
    @Around(value = "webPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 设置自定义线程名称，便于日志追踪
        Thread.currentThread().setName("thread-" + UUID.randomUUID());

        // 获取当前请求对象
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return proceed(joinPoint);
        }

        // 获取请求URL和参数
        String requestUrl = request.getRequestURI();
        String requestParams = getRequestParameters(joinPoint, request);

        // 获取用户名，用于日志记录
        String userName = getCurrentUserName();

        // 记录接口请求日志
        log.info("接口请求[{}],请求用户：[{}],请求参数：{}", requestUrl, userName, requestParams);

        // 执行目标方法并返回结果
        return logResponse(joinPoint);
    }

    /**
     * 获取当前请求对象
     *
     * @return 当前请求对象
     */
    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取当前用户名称
     *
     * @return 用户名称
     */
    private String getCurrentUserName() {
        return "admin";
    }

    /**
     * 获取请求参数字符串
     *
     * @param joinPoint 切入点对象
     * @param request   请求对象
     * @return 请求参数字符串
     */
    private static String getRequestParameters(JoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
                // GET请求，参数在请求头中
                return convertParameterMapToString(request.getParameterMap());
            } else {
                // 非GET请求，参数在请求体中
                return convertArgsArrayToString(joinPoint.getArgs());
            }
        } catch (Exception e) {
            log.error("日志记录获取请求参数失败", e);
            return null;
        }
    }

    /**
     * 将参数数组转换为字符串
     *
     * @param args 参数数组
     * @return 参数字符串
     */
    private static String convertArgsArrayToString(Object[] args) throws JsonProcessingException {
        if (args == null || args.length == 0) {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (Object arg : args) {
            // 忽略文件、请求和响应对象的序列化
            if (arg instanceof MultipartFile || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                continue;
            }
            params.append(objectMapper.writeValueAsString(arg)).append(",");
        }
        return params.length() > 1 ? params.deleteCharAt(params.length() - 1).toString() : null;
    }

    /**
     * 将参数映射转换为字符串
     *
     * @param parameterMap 参数映射
     * @return 参数字符串
     */
    private static String convertParameterMapToString(Map<String, String[]> parameterMap) throws JsonProcessingException {
        if (parameterMap == null || parameterMap.isEmpty()) {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            params.append(entry.getKey()).append("=");
            if (entry.getValue().length > 1) {
                params.append(objectMapper.writeValueAsString(entry.getValue()));
            } else {
                params.append(entry.getValue()[0]);
            }
            params.append(",");
        }
        return params.deleteCharAt(params.length() - 1).toString();
    }

    /**
     * 执行目标方法
     *
     * @param joinPoint 切入点对象
     * @return 目标方法的返回值
     * @throws Throwable 如果执行过程中发生异常
     */
    private Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    /**
     * 执行目标方法并记录响应日志
     *
     * @param joinPoint 切入点对象
     * @return 目标方法的返回值
     * @throws Throwable 如果执行过程中发生异常
     */
    private Object logResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = proceed(joinPoint);
        log.info("接口响应：{}", objectMapper.writeValueAsString(result));
        return result;
    }

}
