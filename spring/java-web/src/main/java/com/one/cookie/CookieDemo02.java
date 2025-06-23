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
 *  一个网站的cookie数量不能超过20个,所有网站的cookie加起来不能超过300个
 *  一个Cookie不能超过4kb,且受到生命周期时长的限制
 *  浏览器可以禁用cookie
 */
@WebServlet("/servlet/servletDemo02")
public class CookieDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1,创建cookie对象
        Cookie cookie = new Cookie("name", "zhangsan");
        // 2,设置cookie生命周期
        cookie.setMaxAge(60*60);

        //3,设置该cookie的虚拟路径 默认为"/"即服务器的虚拟根路径
        //调用setPath()方法之后就cookie的虚拟路径就成了服务器的虚拟根路径/user/*
        //只有当浏览器的请求URL路径为服务器的虚拟根路径/user/*的情况才会向服务器发送此cookie,否则不携带此cookie
        cookie.setPath("/user");
        // 4,向客户端返回该cookie
        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
