package com.one.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author one
 */
@WebServlet("/cookie/servletDemo03")
public class CookieDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("name", "cookie");
        resp.addCookie(cookie);
        // 获取请求中所有的cookie
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie1 : cookies) {
            if("name".equals(cookie1.getName())){
                resp.setContentType("text/html; charset=uff-8");
                resp.getWriter().write(cookie1.getValue());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
