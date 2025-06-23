package com.one.jsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginStudentServlet")
public class LoginStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1,获取用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //2,判断用户名是否为空
        if(username == null || "".equals(username)){
            resp.sendRedirect("/stu/login.jsp");
        }

        //3,登录成功后将用户数据存储到session当中
        HttpSession session = req.getSession();
        session.setAttribute("username",username);
        session.setAttribute("password",password);

        //4,重定向, 等同sentHeader()方法
        resp.sendRedirect("/jsp_demo/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
