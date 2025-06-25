package com.one.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.one.domain.Address;
import com.one.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: TestJsonController
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/08
 */
@Controller
public class JsonController {
    /**
     * 测试响应的结果不经过ViewResolving视图解析器,直接放在ResponseBody响应体中
     * 测试不写 @ResponseBody注解, 返回结果会进入视图解析器ViewResolving
     * 注解@ResponseBody的作用是将处理器方法的返回结果不经过视图解析器,直接放在响应体中返回给客户端
     * SpringMVC中的处理器方法的形参不经能接收普通数据类型,Object对象, 还能接收HttpServletRequest ,HttpServletResponse cookies Session
     * @param request request
     * @param response response
     */
    @RequestMapping("/test01")
    public void testJson01(HttpServletRequest request,HttpServletResponse response){
        System.out.println("testJson01执行了");
        try {
            //这里返回的结果会经过视图解析器ViewResolving
            response.setContentType("text/html;charset=utf-8");
            //浏览器不会显示这句话内容,要想页面显示这句话,要么用请求包含,要么不进入视图解析器,执行页面跳转
            //或者进入视图解析器,不执行页面跳转
            response.getWriter().write("output String to ResponseBody11111");
            request.getRequestDispatcher("/test02").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * /WEB-INFO/pages/happy.jsp相当于第二个Servlet,请求转发和重定向的内容都不会在页面上显示
         *
         * WEB-INF是Java的WEB应用的安全目录。所谓安全就是客户端无法访问，只有服务端可以访问的目录。
         * 如果想在页面中直接访问其中的文件，必须通过web.xml文件对要访问的文件进行相应映射才能访问。
         * 若放在WEB-INF中，则必须修改resources映射，如：
         * <resources mapping="/layers/**" location="/WEB-INF/jsp/layers" />
         * mapping对应的是访问应用程序时的地址；
         * location对应应用程序中文件位置
         */
    }



    /**
     * 返回字符串
     * 使用@ResponseBody注解, 那么就是将方法的返回值放在响应体中返回客户端, 不用进过视图解析器
     * @param response response
     * @return String
     */
    @RequestMapping("/test02")
    @ResponseBody
    public String testJson02(HttpServletResponse response){
        System.out.println("testJson02执行了");
        try {
            //这里返回的结果会经过视图解析器ViewResolving
            response.setContentType("text/html;charset=utf-8");
            //浏览器不会显示这句话内容,要想页面显示这句话,要么用请求包含,要么不进入视图解析器,执行页面跳转
            response.getWriter().write("output String to ResponseBody22222");
            response.getWriter().write("output String to ResponseBody33333");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "forward:/WEB-INF/pages/happy.jsp";
    }


    /**
     * 导入Jackon依赖,不经过 ViewResolving视图解析器,
     * HandlerAdapter直接将对象转换为json字符串放入响应体中写出到浏览器页面,
     * @return json
     */
    @RequestMapping("test03")
    @ResponseBody
    public User testJson03(){
        User user = new User();
        user.setId("001");
        user.setName("lisi");
        user.setAge(24);
        user.setAddress(new Address("hubei","wuhan","武湖街道"));
 //控制台输出: {"id":"001","name":"lisi","age":24,"address":{"province":"hubei","city":"wuhan","rode":"武湖街道"},"hobby":null,"addresses":null}
        return user;
    }

    /**
     * 对象转json的本质
     * @param response
     */
    @RequestMapping("test04")
    @ResponseBody
    public void testJson04(ServletResponse response){
        System.out.println("test04执行了");
        User user = new User();
        user.setId("001");
        user.setName("lisi");
        user.setAge(24);
        user.setAddress(new Address("hubei","wuhan","武湖街道"));
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(user);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
