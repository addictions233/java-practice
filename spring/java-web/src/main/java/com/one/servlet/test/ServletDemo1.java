package com.one.servlet.test;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * @ClassName ServletDemo1   web.xml为web项目的入口,等同于java中的main方法
 * @Description
 *  生成自定义Servlet有三种方式
 *     1, 实现Servlet接口的方式生成自定义Servlet, 重写Servlet的几个生命周期函数
 *     2, 继承 GenericServlet抽象类
 *     3, 继承HttpServlet类, 建议重写 doGet()方法和doPost()方法
 * 配置Servlet的访问路径有两种方式:
 *      1) 使用@WebServlet注解的方式配置Servlet访问路径, (基于注解)
 *      2) 在web.xml中使用servlet标签配置Servlet的访问路径的效果一样 (基于配置文件)
 * @author one
 * @date 2022/03/29
 */
@WebServlet(name = "servletDemo1", urlPatterns = "/servletDemo1")  //等同于在web.xml中使用<servlet>标签配置
public class ServletDemo1 implements Servlet {
    /**
     * 当servlet对象被创建时,或调用此方法(懒汉式的创建模式,当第一个被请求时创建servlet对象)
     * @param servletConfig servlet配置对象
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("servletDemo1中的init方法执行了...");
    }

    /**
     * 获取初始化的配置信息
     * @return
     */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 有客户端的http请求到达服务器时,会调用servlet中的service方法进行处理
     * @param servletRequest 请求对象
     * @param servletResponse 响应对象
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse)  {
        System.out.println("ServletDemo1中的service方法执行了...");
    }

    /**
     * 返回servlet的基本信息
     * @return
     */
    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * 当servlet对象被销毁(关闭服务器时),并被垃圾回收器回收时会调用此方法
     */
    @Override
    public void destroy() {
        System.out.println("servletDemo1中的 destroy方法执行了....");
    }
}
