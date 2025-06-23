package com.one.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author one
 * Request对象就是为了获取请求参数,
 *      请求分为: 请求行  Get/Post请求  url Http1.1协议
 *               请求头
 *               请求体
 */
@WebServlet("/myServlet01")
public class MyServlet01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String localAddr = req.getLocalAddr();
        System.out.println("localAddr = " + localAddr);

        String localName = req.getLocalName();
        System.out.println("localName = " + localName);

        int localPort = req.getLocalPort();
        System.out.println("localPort = " + localPort);

        // 获取请求的ip地址
        String remoteAddr = req.getRemoteAddr();
        System.out.println("remoteAddr = " + remoteAddr);

        String remoteHost = req.getRemoteHost();
        System.out.println("remoteHost = " + remoteHost);

        int remotePort = req.getRemotePort();
        System.out.println("remotePort = " + remotePort);

        // 获取虚拟目录的名称
        String contextPath = req.getContextPath();
        System.out.println("contextPath = " + contextPath);

        String realPath = req.getRealPath("/");
        System.out.println("realPath = " + realPath);

        //获取统一资源标识符 Uniformed Resource Identifier 只带虚拟路径和文件名
        String requestURI = req.getRequestURI();
        System.out.println("requestURI = " + requestURI);

        //获取统一资源位置符 Uniformed Resource location 带ip地址和端口
        StringBuffer requestURL = req.getRequestURL();
        System.out.println("requestURL = " + requestURL);


        //获取请求参数
        String username = req.getParameter("username");
        System.out.println("username:" + username);
        // 获取请求域中的属性值
        Object attribute = req.getAttribute("attribute");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
