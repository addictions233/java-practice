package com.one.request;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * 继承自GenericServlet的servlet
 * @author one
 */
@WebServlet("/myServlet02")
public class MyServlet02 extends GenericServlet {
    /**
     * servlet最初的规范并不是只想作为web服务器,也就是不是只支持http协议,还支持其他的通信协议,所用
     * GenericServlet的service()方法的参数是servletRequest而不是httpServletRequest
     * @param servletRequest servlet请求对象
     * @param servletResponse servlet响应对象
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse)  {
        servletRequest.setAttribute("encoding","gbk");
        String name = servletRequest.getParameter("name");
        System.out.println("name:" + name);
    }
}
