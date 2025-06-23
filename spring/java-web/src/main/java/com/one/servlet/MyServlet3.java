package com.one.servlet;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * ServletConfig对象: servlet的初始化参数配置对象
 * @author one
 */
public class MyServlet3 extends HttpServlet {
    /**
     * servlet的初始化参数配置对象
     */
    private ServletConfig servletConfig;

    /**
     * servletConfig对象是servlet的初始化参数配置对象, 生命周期和servlet的生命周期一样
     * init()方法一般会用来读取配置文件, 通过init()方法对servletConfig对象进行赋值
     * @param config servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletConfig = config;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 使用servletConfig对象
        // 1,获取参数配置,通过key获取value
        String encoding = servletConfig.getInitParameter("encoding");
        System.out.println("encoding:" + encoding);

        // 2,获取所有的配置参数的枚举项
        Enumeration<String> parameterNames = servletConfig.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = servletConfig.getInitParameter(name);
            System.out.println(name + ":" + value);
        }

        String one = servletConfig.getInitParameter("one");
        System.out.println("one:" + one);

        // 3,获取servlet的名称, 即<servlet-name>标签中配置的名称
        String servletName = servletConfig.getServletName();
        System.out.println("servletName:" + servletName);

        // 4, 获取servletContext 全局域对象
        ServletContext context = servletConfig.getServletContext();
        System.out.println("context = " + context);
        //获取项目的真实路径
        String realPath = context.getRealPath("/");
        System.out.println("realPath = " + realPath);

        //获取项目的虚拟路径
        String contextPath = context.getContextPath();
        System.out.println("contextPath = " + contextPath);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }


}
