package com.one.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @ClassName: ListenerDemo03
 * @Description: servletContext的监听器对象: 基于配置文件web.xml来配置listener
 * @Author: one
 * @Date: 2022/04/16
 */
public class ListenerDemo03 implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        System.out.println("servletContext is created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        System.out.println("servletContext is destroyed");
    }
}
