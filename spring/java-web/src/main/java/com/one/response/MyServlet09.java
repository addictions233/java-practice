package com.one.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应重定向response#sendRedirect()
 * @author one
 */
@WebServlet("/myServlet12")
public class MyServlet09 extends HttpServlet {

    /**
     * 接收重定向的请求
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write("关我屁事!");
        System.out.println("myServlet09开始执行了!");
//        resp.sendRedirect("myServlet08");
        System.out.println("myServlet09执行结束了!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
