package com.one.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应重定向 response#sendRedirect()
 * @author one
 */
@WebServlet("/myServlet08")
public class MyServlet08 extends HttpServlet {

    /**
     * 测试响应重定向
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write("我不好!");
        System.out.println("myServlet11开始执行了!");
        // 重定项需要带虚拟路径
        resp.sendRedirect(req.getContextPath() + "/myServlet12");
        System.out.println("myServlet11执行结束了!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
