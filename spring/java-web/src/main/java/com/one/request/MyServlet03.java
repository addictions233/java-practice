package com.one.request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求转发requestDispatcher#forward()方法
 * @author one
 */
@WebServlet("/myServlet03")
public class MyServlet03 extends HttpServlet {

    /**
     * 测试请求转发
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 请求对象 req也是域对象,可以在一次请求中和请求转发对象共享数据
        req.setAttribute("name","zhangsan");
        req.setAttribute("encoding","gbk");
        RequestDispatcher dispatcher = req.getRequestDispatcher("myServlet04");
        dispatcher.forward(req,resp);
        System.out.println("MyServlet03执行完了");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
