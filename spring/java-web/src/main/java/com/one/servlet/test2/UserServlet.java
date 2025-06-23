package com.one.servlet.test2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author one
 */
@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求和响应的字符集为utf8
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        //获取请求参数
        String username = req.getParameter("username");

        //对请求参数进行判断
        if("zhangsan".equals(username)){
            //将结果返回给页面
            resp.getWriter().write("<font color='red'>用户名已注册!</font>");
        } else{
            //将结果返回给页面
            resp.getWriter().write("<font color='red'>用户名可用<font>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
