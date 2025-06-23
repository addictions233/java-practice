package com.one.servlet.test;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * @ClassName: ServletDemo2
 * @Description: 2,第二种方式继承 GenericServlet抽象类
 * @Author: one
 * @Date: 2022/03/29
 */
@WebServlet(name = "servletDemo2", urlPatterns = "/servletDemo2")
public class ServletDemo2 extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("ServletDemo1 执行了...");
    }
}
