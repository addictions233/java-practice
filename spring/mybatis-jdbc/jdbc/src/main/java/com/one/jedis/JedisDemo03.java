package com.one.jedis;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jedisDemo03")
public class JedisDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        //从jedis数据库中取值
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String tempReturn = jedis.get("temp");

        //将该值转化为json对象
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(tempReturn);
//        System.out.println(json);

        //将json对象返回到前端
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
