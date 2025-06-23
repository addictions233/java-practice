package com.one.filter;

import com.one.exception.ApplicationException;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName: WebFilter
 * @Description: 实现servlet规范中的过滤器
 *                  servlet容器(Tomcat)提供的是Filter接口, GenericFilter抽象类来实现Filter过滤器
 *                  而 GenericFilterBean对象是Spring提供的过滤器抽象类,实现了Filter接口
 * @Author: one
 * @Date: 2022/03/24
 */
@Order(1) // 定义过滤器的执行顺序
@WebFilter(filterName = "userCheckFilter", urlPatterns = "/*") // /*表示拦截所有请求
public class UserCheckFilter extends GenericFilterBean {
    /**
     * 进行过滤器的初始化操作
     * @throws ServletException
     */
    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    /**
     * 对请求进行拦截或者放行,在doFilter()方法中执行
     * @param request 请求对象
     * @param response 响应对象
     * @param chain 过滤器链对象
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("UserCheckFilter过滤器执行之前");
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String userId = httpServletRequest.getHeader("userId");
        // 校验用户id是否存在
        if (StringUtils.isEmpty(userId)) {
            // 抛出自定义异常,请求还会到达Interceptor
            throw new ApplicationException(505,"请求头中没有用户Id");
        }
        //对请求进行放行,到下一个过滤器链上的下一个过滤器,如果后面没有filter,就到达对应的处理器handler
        chain.doFilter(request,response);
        // 在所有的请求流程都走完后,返回时会执行过滤器中下面的方法
        System.out.println("UserCheckFilter过滤器执行之后");
    }

    /**
     * 在Filter销毁前执行某些资源回收操作
     */
    @Override
    public void destroy() {
        super.destroy();
    }
}
