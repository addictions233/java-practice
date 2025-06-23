package com.one.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author one
 *   Cookies是用来将指定信息从客户端携带到服务器端的会话技术
 *      依赖cookies实现的功能有: 1,一段时间内用户可以免密码登录网站
 *                              2, 京东购物车的商品数量信息也是放在cookie中的
 *                              ....
 *   JSESSIONID是服务器创建的cookie,用来管理session的
 */
@WebServlet("/servletDemo01")
public class CookieDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应字符集,否则响应中文会乱码
        resp.setContentType("text/html; charset=utf-8");
        // 获取响应字符流
        resp.getWriter().write("欢迎访问本网站! THERE IS COOKIES");

        // 在服务器端创建cookie对象 , cookie的name和value属性都是字符串,必须显性初始化
        Cookie cookie = new Cookie("time",System.currentTimeMillis()+"");

        // 设置cookie的最大生命周期
        cookie.setMaxAge(3600);

        // 服务器端创建cookie,并且通过resp对象将cookie对象返回给浏览器客户端,并命令客户端保存cookie对象
        resp.addCookie(cookie);

        // 每次客户端请求时都会携带cookies,所以服务器端可以从req请求对象中获取cookies
        Cookie[] cookies = req.getCookies();
        // 遍历cookies
        for (Cookie cookie1 : cookies) {
            if("time".equals(cookie1.getName())){
                String value = cookie1.getValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                resp.getWriter().write("<br/>");
                // 将cookies中存储的时间戳响应给客户端
                resp.getWriter().write(sdf.format(new Date(Long.parseLong(value))));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
