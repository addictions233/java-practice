package com.one.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author one
 * 监听session会话对象的生命周期可以展现当前网站的实时在线人数
 */
@WebListener
public class ListenerDemo02 implements HttpSessionListener {
    int num = 0;

    /**
     * 会话创建
     * @param se session事件对象
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        num++;
        //创建HttpSession对象时,会调用sessionCreated()方法,
        //访问index.jsp,会创建Session对象
        System.out.println("-----当前存活的session个数-----");
        //获取会话域对象
        HttpSession session = se.getSession();
        //获取应用域对象
        ServletContext servletContext = session.getServletContext();
        //利用应用域设置全局共享数据
        servletContext.setAttribute("onlineNum",num);
    }

    /**
     *  当用户退出
     * @param se session事件对象
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("----session销毁了-----");
        num--;
    }
}
