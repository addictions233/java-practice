package com.one.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author one
 *  Filter过滤器的生命周期函数 Filter#init()函数和 Filter#destroy()函数
 */
@WebFilter("/*")
public class FilterDemo03 implements Filter {

    private FilterConfig filterConfig;

    /**
     * 启动服务器调用init()方法
     * @param filterConfig 过滤器的配置信息
     */
    @Override
    public void init(FilterConfig filterConfig)  {
        this.filterConfig = filterConfig;
        System.out.println("Filter过滤器的 init方法执行了...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //设置响应字符集,解决中文乱码问题
        servletResponse.setContentType("text/html;charset=UTF-8");
        System.out.println("FilterDemo3过滤器中的 doFilter方法执行了");
        //对请求进行方法  filterChain对象只用一个功能就是对请求进行放行,且该类对象是由Servlet向我们提供,我们可以直接使用
        filterChain.doFilter(servletRequest,servletResponse);
    }

    /**
     *  卸载应用或者关闭服务器调用此方法
     */
    @Override
    public void destroy() {
        System.out.println("Filter过滤器的 destroy方法执行了..");
    }
}
