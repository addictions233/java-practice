package com.one.response;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author one
 *  响应文件流: 将图片文件作为响应内容
 */
@WebServlet("/myServlet06")
public class MyServlet06 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取应用上下文对象
        ServletContext context = super.getServletContext();
        // 根据相对路径获取绝对路径,web项目所有的相对路径都是相对于web文件夹的相对路径
        String realPath = context.getRealPath("/imgs/greatwall.jpg");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(realPath));
        System.out.println(realPath);
        //获取响应对象的字节输出流
        ServletOutputStream sos = resp.getOutputStream();
        byte[] bys = new byte[1024];
        int length;
        while ((length = bis.read(bys)) != -1) {
            // 响应字节流
            sos.write(bys,0, length);
        }
        // 关闭资源 servletOutputStream流对象是http协议的,不需要我们关闭资源
        bis.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
