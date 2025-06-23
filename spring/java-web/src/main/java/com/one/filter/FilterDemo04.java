package com.one.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author one
 * 1. 基于配置文件配置Filter
 *      在web.xml配置文件中对FilterDemo04进行配置
 * 2. 基于注解@WebFilter配置Filter
 *      * 配置过滤路径  /*代表拦截所以请求
 *       每次客户端发送的请求都会拦截,默认具有DispatcherType.REQUEST属性, 只对请求request进行拦截
 *       如果配置Dispatcher.FORWARD表示对请求转发也会进行拦截
 */
//@WebFilter(value="/*",dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class FilterDemo04 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filterDemo04执行了....");
        // 过滤器对请求进行放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
