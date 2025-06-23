package com.one.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接收前面servlet转发的请求
 * @author one
 */
@WebServlet("/myServlet04")
public class MyServlet04 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 请求由 myServlet03转发到myServlet04
        Object name = req.getAttribute("name");
        System.out.println("name:" + name);
        Object encoding = req.getAttribute("encoding");
        System.out.println("encoding = " + encoding);
        System.out.println("myServlet04执行了!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
