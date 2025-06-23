package com.one.session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author one
 *  Session: 会话域,服务器端的会话管理技术,  cookie是客户端的会话管理技术
 *  session技术依赖cookie而存在, 如果禁止使用cookie,那么session的功能也就消失了
 *  JSESSIONID是session会话在服务器端的唯一标识
 */
//@WebServlet("/servletDemo01")
public class SessionDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        // 客户端第一次向服务器发起请求时,建立会话时,JSEESIONID就从服务器端放在cookie中返回到器客户端
        // 所以session的存在依赖cookie
        HttpSession session = req.getSession(true);
        System.out.println(session);
        System.out.println(session.getId());

        session.setAttribute("username",username);

        //实现url重写  相当于在地址栏拼接了 jsessionid
        resp.getWriter().write("<a href="+resp.encodeURL("http//"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
