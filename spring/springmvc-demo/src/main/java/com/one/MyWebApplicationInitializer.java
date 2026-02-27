package com.one;

import com.one.config.WebMvcConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author one
 * 使用Tomcat提供的SPI机制, 替代web.xml配置文件. 来使用零配置方式启动springmvc
 * 采用SPI机制启动需要删除web.xml配置文件, 否则Tomcat还是会优先读web.xml文件
 * springmvc 零xml配置提供的默认实现是 AbstractAnnotationConfigDispatcherServletInitializer
 * springboot 提供的默认实现是 SpringBootServletInitializer
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebMvcConfig.class);

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1); // 设置DispatcherServlet在容器启动时就加载
        registration.addMapping("/app/*"); // 设置DispatcherServlet处理的路径
    }
}
