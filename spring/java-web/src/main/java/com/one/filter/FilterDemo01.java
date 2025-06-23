package com.one.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author one
 *  Filter快速入门:
 *      1,定义一个类实现javax.servlet.Filter接口,或者继承javax.servlet.GenericFilter抽象类
 *      2,重写doFilter()方法
 *      3,配置 (基于注解@WebFilter或者 基于web.xml配置文件)
 *  过滤器Filter作用:
 *      可以拦截用户的请求!(过滤请求)  静态页面,jsp,Servlet都可以被拦截
 */
//@WebFilter("/*")  //用注解的方式配置过滤器
public class FilterDemo01 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filterDemo01的init方法执行了!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("FilterDemo01 放行前~");
        System.out.println("FilterDemo01 doFilter方法执行了");
        // 对拦截的请求进行放行,到达过滤器链的下一个过滤器,如果后面没有过滤器,就到达对应的请求servlet
        filterChain.doFilter(servletRequest,servletResponse);
        // 下面这行代码是在servlet中的代码执行之后再执行的
        System.out.println("FilterDemo01 放行后");
    }

    @Override
    public void destroy() {
        System.out.println("FilterDemo01 destroy方法执行了!");
    }
}
