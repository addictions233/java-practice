package com.one.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author one
 * Servelt的三种实现方式:
 *      1,实现Servlet接口,
 *      2,继承GenericServlet类
 *      3,继续HttpServlet类
 *      Servelt是单例模式创建对象: 默认的初始化方式是第一次请求访问,才初始化servlet, 懒加载的方式
 *      如果想要服务器启动时就创建servlet对象,就在配置中使用 loadOnStartup 默认值为-1 值越小创建的优先级越高
 */
public class MyServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("该方法执行了!");
        ServletContext servletContext = this.getServletContext();

        // 获取全局变量
        Object username = servletContext.getAttribute("username");
        System.out.println("username的值为:" + username);

        // 删除全局变量
        servletContext.removeAttribute("username");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    /**
     * servlet生命周期函数: 销毁对象
     */
    @Override
    public void destroy() {
        System.out.println("MyServlet1对象销毁了....");
    }

    /**
     * servlet生命周期函数: 初始化创建
     * @param servletConfig servlet配置对象
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        String name = servletConfig.getInitParameter("name");
        System.out.println(name);
        System.out.println("MyServlet1对象创建了....");
    }
}
