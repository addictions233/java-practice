package com.one.session.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        System.out.println(req.getParameter("username")+"登录了"+req.getParameter("tel"));

        //使用session对象存储用户信息,用于一次会话中传递信息
        HttpSession session = req.getSession();
        session.setAttribute("username",req.getParameter("username"));
        session.setAttribute("tel",req.getParameter("tel"));
        session.setAttribute("money",req.getParameter("money"));

        //创建cookie对象,用手机号作为用户唯一标识
        Cookie cookie = new Cookie("userid", req.getParameter("tel"));
        //设置cookie对象作用的请求路径,服务器端的"/"代表项目的根路径
        cookie.setPath("/");
        //让客户端保存该cookie对象,该次会话每次客户端发送报文都会携带该cookie
        resp.addCookie(cookie);

        // 页面在2秒之后自动跳转到index.html页面
        resp.getWriter().write("2秒后页面自动跳转~");

        // 相当于浏览器重新发起新的页面请求,所以这里的"/"相当于服务器的根站点
        resp.setHeader("Refresh","2;URL=/session_demo/index.html");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
