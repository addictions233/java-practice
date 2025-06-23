package com.one.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author one
 * Response对象就是为了响应结果数据
 *      响应分为: 状态行 200/500 http1.1协议 其他信息
 *               响应头
 *               响应体
 */
@WebServlet("/myServlet05")
public class MyServlet05 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应的字符集
        resp.setContentType("text/html; charset=UTF-8");
        System.out.println("MyServlet06执行了!");
        // 通过response对象获取字符流响应
        PrintWriter writer = resp.getWriter();
        // 响应字符串内容
        writer.write("你好!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
