package com.com.springbootmvc.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
 
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化代码（可选）
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 在这里可以对请求进行预处理操作，例如检查请求头、参数等
        System.out.println("myFilter execute");
        // 继续执行过滤器链中的下一个过滤器或目标资源
        chain.doFilter(request, response);
 
        // 在这里可以对响应进行后处理操作，例如修改响应头、内容等
    }
 
    @Override
    public void destroy() {
        // 销毁代码（可选）
    }
}