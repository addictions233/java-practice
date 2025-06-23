package com.one.servlet.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: ServletDemo3  web开发通常使用这种方式
 * @Description: 3,继承HttpServlet类, 建议重写 doGet()方法和doPost()方法
 * @Author: one
 * @Date: 2022/03/29
 */
@WebServlet(name = "servletDemo3", urlPatterns = "/servletDemo3")
public class ServletDemo3 extends HttpServlet {
    /**
     * 接收get请求
     * @param req 请求对象
     * @param resp 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServletDemo3 执行了...");
        super.doGet(req,resp);
    }

    /**
     * 接收post请求
     * @param req 请求对象
     * @param resp 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
