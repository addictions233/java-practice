package com.one.servlet.test2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author one  web.xml为web项目的入口,等同于java中的main方法
 * 项目中有多少个Servlet,就将这些Servlet封装到Tomcat的list集合中
 * Tomcat除了作为web容器之外,实现了java EE中的servlet规范,作为了Servlet的容器(Servlet api的jar包)
 * 以web项目为单位 ---> 读取web.xml配置文件  -----> 扫描到多少个Servelt
 * Tomcat两大核心组件: Connector: 建立serverSocket连接    Container: 表示一个web项目
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if("张三".equals(username) && "123".equals(password)){
            User user = new User(username, password);
            req.setAttribute("user",user);
            // 请求转发到successServlet
            RequestDispatcher dispatcher = req.getRequestDispatcher("/successServlet");
            dispatcher.forward(req,resp);
        } else{
            // 请求转发到failServlet
            RequestDispatcher dispatcher = req.getRequestDispatcher("/failServlet");
            dispatcher.forward(req,resp);
        }
    }
}
