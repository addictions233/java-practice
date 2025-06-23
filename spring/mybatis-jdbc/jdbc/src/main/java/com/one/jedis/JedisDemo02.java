package com.one.jedis;

import redis.clients.jedis.Jedis;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jedisDemo02")
public class JedisDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        //获取参数
        String tempValue = req.getParameter("temp");

        //将该参数值存储到redis数据库中
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.set("temp", tempValue);

        // 将请求重定向到 JedisDemo03中进行查询
        RequestDispatcher dispatcher = req.getRequestDispatcher("/jedisDemo03");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
