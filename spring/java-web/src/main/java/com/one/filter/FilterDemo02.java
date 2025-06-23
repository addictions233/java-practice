package com.one.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author one
 *  多个过滤器的执行顺序:
 *      1,如果使用@WebFilter注解的方式配置,由类名的ASCII排序决定执行的先后顺序
 *      2,如果在web.xml中配置的方式,由在配置文件的先后顺序决定执行顺序
 */
@WebFilter("/*")
public class FilterDemo02 extends GenericFilter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FilterDemo02 init方法执行了!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("FilterDemo02 放行前~");
        System.out.println("FilterDemo02 doFilter方法执行了");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("FilterDemo02 放行后");
    }

    @Override
    public void destroy() {
        System.out.println("FilterDemo02 destroy方法执行了!");
    }
}
