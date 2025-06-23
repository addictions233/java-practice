package com.one.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author one
 * 一,servlet的访问路径的两种配置方式:
 *          1,在web.xml使用servlet标签的形式配置
 *          2,在类上使用@WebServlet注解的方式配置
 *      由于Servlet运用了单例模式，即整个应用中只有一个实例对象
 * 二,ServletConfig对象:
 *      为servlet提供初始化配置,每一个servlet初始化时都有一个专属的 servletConfig对象
 *      servletConfig对象的生命周期和servlet的对象的生命周期一模一样
 *      作用: servletConfig是servlet的初始化参数配置对象, 可以通过servletConfig对象获取servletContext对象
 * 三,ServeltContext对象: (全局域对象: 用来实现数据的共享)
 *      作用: 可以配置和获取应用的全局初始化参数, 作为域对象实现servlet之间的数据共享
 *     生命周期: 一个web应用只要一个ServletContext对象, serveltContext是整个web应用中最大的域对象(全局域)
 */
@WebServlet(value = "/myServlet2", loadOnStartup = 1, initParams = {@WebInitParam(name = "one", value = "coder") })
// loadOnStartup默认值为-1  值越小,创建的优先级越高
// 使用@WebInitParam配置servletConfig对象
public class MyServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取全局域对象
        ServletContext servletContext = super.getServletContext();
        System.out.println("context = " + servletContext);
        // 使用全局域对象
        // 根据参数名称获取全局配置的参数值
        String value = servletContext.getInitParameter("globalEncoding");
        System.out.println("获取的参数值为:" + value);
        // 获取web应用的虚拟路径
        String contextPath = servletContext.getContextPath();
        System.out.println("web应用的虚拟路径为:" + contextPath);
        // 根据项目部署的绝对路径
        String realPath = servletContext.getRealPath("/");
        System.out.println("web应用的绝对路径为:" + realPath);

        // 使用全局域对象设置共享变量
        servletContext.setAttribute("username", "one");
        // 获取全局变量
        Object username = servletContext.getAttribute("username");
        System.out.println("username的值为:" + username);
        // 删除全局变量
        servletContext.removeAttribute("username");

        // 获取servlet的配置类对象
        ServletConfig servletConfig = super.getServletConfig();
        // 获取@WebInit注解中的配置
        String one = servletConfig.getInitParameter("one");
        System.out.println("one:" + one);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doGet(req,resp);

    }
}
