package com.one.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  响应重定向 response#sendRedirect()
 * @author one
 */
@WebServlet("/myServlet07")
public class MyServlet07 extends HttpServlet {

    /**
     * 测试响应重定向
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write("你好!");
        System.out.println("myServlet07开始执行了!");
        // 响应重定向, 让前端对新的servlet发起请求
        resp.sendRedirect("myServlet08");
        System.out.println("myServlet07执行结束了!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
