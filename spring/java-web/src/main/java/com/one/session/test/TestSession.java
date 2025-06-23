package com.one.session.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author one
 */
@WebServlet("/testSession")
public class TestSession extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        System.out.println("TestSession执行了!");
        //取出session中保存的属性数据,session数据是保存在服务器端的,在浏览器页面输出给用户
        HttpSession session = req.getSession();
        System.out.println(session.getAttribute("money"));
        resp.getWriter().write("欢迎回来~"+ session.getAttribute("username")+"你的余额是"+session.getAttribute("money"));
        // cookie数据是保存在客户端的
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if("userid".equals(cookie.getName())){
                resp.getWriter().write("<br/>");
                resp.getWriter().write(cookie.getValue());
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
