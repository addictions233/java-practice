package com.one.session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *  演示 浏览器 cookie禁用之后效果: 禁用cookie之后session也就无法使用了
 * @author one
 */
//@WebServlet("/servletDemo03")
public class SessionDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // getSession()方法的默认参数为true,如果是false,服务器不会为客户端创建session会话的
        HttpSession session = req.getSession(false);
        resp.setContentType("text/html; charset=uft-8");
        if(session == null){
            resp.getWriter().write("为了不影响正常使用,请不要禁用Cookies!!");
        }
        System.out.println(session);
        System.out.println(session.getId());

        Object username = session.getAttribute("username");

        resp.getWriter().write(username+"");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
