package com.one.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author one
 * 监听器能够监听某些对象创建或者销毁,当某些对象执行了创建或者销毁,监听器采用了观察者设计模式
 * 如何编写自定义的监听器类?
 *      1,实现接口  Servlet一共提供了八个接口可供实现,一般选择符合需求的一个接口
 *          常见的Listener接口:
 *              ServletContextListener, 用于监听ServletContext应用上下文对象的创建和销毁,
 *              HttpSessionListener, 用于监听HttpSession会话对象的创建和销毁
 *              ServletRequestListener, 用于监听ServletRequest请求对象的创建和销毁
 *              其他Listener
 *      2,实现对应的listener接口,并重写生命周期方法
 *      3,配置 基于注解@WebListener配置或者基于配置文件web.xml配置
 *  案例需求:
 *      监听request请求对象的生命周期可以展现当前页面被访问的次数
 */
@WebListener
public class ListenerDemo01 implements ServletRequestListener {
    int num= 0;

    /**
     * 客服端访问服务器时传入的 HttpServletRequest对象封装到了ServletRequestEvent事件对象中,
     * 所以可以用sre直接获取HttpServletRequest对象
     * @param sre 请求事件对象
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        //获取请求域对象
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        //获取统一资源标识符,区别于URL(统一资源路径符)
        String requestURI = request.getRequestURI();
        //判断每一个请求对象的标识符是否包含"index.jsp"
//        if(requestURI.contains("index.jsp")) {
            num++;
//        }
        System.out.println("----request对象创建了-----"+(num));
        ServletContext servletContext = sre.getServletContext();
        servletContext.setAttribute("indexNum",num);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("----request对象销毁了-----");
    }
}
