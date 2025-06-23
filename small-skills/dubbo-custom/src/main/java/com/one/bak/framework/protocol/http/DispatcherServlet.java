package com.one.bak.framework.protocol.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: tomcat中转发请求
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class DispatcherServlet extends HttpServlet {

    /**
     * Receives standard HTTP requests from the public
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new HttpServerHandler().handler(req, resp);
    }
}
