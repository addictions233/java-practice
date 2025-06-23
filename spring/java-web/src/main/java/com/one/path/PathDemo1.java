package com.one.path;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author one
 *  ServletDemo1, ServletDemo2,ServletDemo3的访问url路径在web.xml中用标签定义
 *  ServletDemo4, ServletDemo5,ServletDemo6的访问url路径用@WebServlet注解定义
 *  **重点**:
 *   如果我们web项目不设置虚拟路径, 那么项目中的 "/" 即 "xxx:8080/"是指项目打包发布之后的根路径,
 *   即指向硬盘中out目录下 xxx\out\artifacts\path_demo_war_exploded\这个文件路径
 *  如果我们设置了虚拟路径 "/webapp"  那么这个虚拟路径是资源文件在浏览中分访问时必须携带的虚拟路径
 *  即浏览器中输入的"xxx:8080/webapp/" 指向的才是项目打包之后发布的根路径:
 *  硬盘中out目录下 xxx\out\artifacts\path_demo_war_exploded\这个文件路径
 */
public class PathDemo1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取全局应用域配置对象
        ServletContext servletContext = super.getServletContext();
        String contextPath = servletContext.getContextPath();
        //  虚拟路径为:/webapp
        System.out.println("虚拟路径为:" + contextPath);
        String realPath = servletContext.getRealPath("/");
        // 根路径的绝对路径为:E:\IdeaProjects\group9-wanjunjie\javaweb-core\out\artifacts\path_demo_war_exploded\
        System.out.println("根路径的绝对路径为:" + realPath);

        // 获取b.txt的绝对路径  "/"代表项目的根路径, 绝对路径中是文件系统中的真实路径,不存在虚拟路径的
        String bRealPath = servletContext.getRealPath("/b.txt");
        // b.txt的绝对路径:E:\IdeaProjects\group9-wanjunjie\javaweb-core\out\artifacts\path_demo_war_exploded\b.txt
        System.out.println("b.txt的绝对路径:" + bRealPath);

        // 获取c.txt的绝对路径
        String cRealPath = servletContext.getRealPath("/WEB-INF/c.txt");
        // c.txt的绝对路径:E:\IdeaProjects\group9-wanjunjie\javaweb-core\out\artifacts\path_demo_war_exploded\WEB_INF\c.txt
        System.out.println("c.txt的绝对路径:" + cRealPath);

        // 获取a.txt的绝对路径 src目录下的内容最后会打包成为WEB-INF目录下的的classes目录下的资源
        String aRealPath = servletContext.getRealPath("/WEB-INF/classes/a.txt");
        System.out.println("a.txt的绝对路径:" + aRealPath);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
