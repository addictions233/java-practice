package com.one.controller;

import com.one.domain.DateDTO;
import com.one.domain.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName: UserController
 * @Description: @Controller注解作用: 设置当前类为SpringMVC的控制器类,创建Bean对象放在IOC核心容器中
 * @Author: one
 * @Date: 2020/12/06
 */
@Controller
public class ParamController {
    /**
     * Note:  @RequestMapping注解用来设置当前方法的访问路径
     * @return jsp
     */
    @RequestMapping("/save")
    public String save(@RequestParam Map<String,Object> map){
        System.out.println(map);
        System.out.println("user mvc controller is running");
        // 设置执行完之后的跳转页面
        return "success.jsp";
    }

    /**
     * 一般都是使用@DateTimeFormat把传给后台的时间字符串转成Date，使用@JsonFormat把后台传出的Date转成时间字符串，
     * 但是@DateTimeFormat只会在非json数据上生效，即如果@DateTimeFormat放到@RequestBody下是无效的。
     * 在@RequestBody中则可以使用@JsonFormat把传给后台的时间字符串转成Date，
     * 也就是说@JsonFormat其实既可以把传给后台的时间字符串转成Date也可以把后台传出的Date转成时间字符串。
     *
     * 设置日期格式的转换问题 SpringMVC提供了@DateTimeFormat注解用来接收前端传入的时间格式, 转换为Date对象
     * @param date date
     * @return jsp
     */
    @RequestMapping(path = "/testDate", method = RequestMethod.GET)
    public String testDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        System.out.println(date);
        return "success.jsp";
    }

    @RequestMapping(path = "/testDate2", method = RequestMethod.POST)
    public String testDat2(@RequestBody DateDTO dateDTO) {
        System.out.println(dateDTO);
        return "success";
    }

    /**
     * 测试基本数据类型请求参数传递
     * @param id id
     * @param name name
     * @return jsp
     */
    @RequestMapping("/findUser01")
    public String findUser01(int id,String name){
        System.out.println("id="+id);
        System.out.println("name="+name);
        return "success";
    }

    //测试简单的POJO数据类型的请求参数
    @RequestMapping("findUser02")
    public String findUser02(User user){
        System.out.println("user="+user);
        return "success";
    }

    //测试复杂类型的POJO数据类型的请求参数
    @RequestMapping("/findUser03")
    public String findUser03(User user){
        System.out.println(user);
        return "success";
    }

    //测试配置视图解析器 ViewResolver
    @RequestMapping("/show01")
    public String showPage(){
        System.out.println("user mvc controller is running...");
        return "happy";
    }

    /**
     * 使用ModelAndView类型参数进行数据传递,将该对象作为返回值经过视图解析器传解析之后递给客户端
     * Model: 模型, 用于封装数据
     * View: 视图, 用于展示数据
     * ModelAndView: 用于封装模型和视图
     * @param modelAndView modelAndView
     * @return
     */
    @RequestMapping("/show02")
    public ModelAndView showPage(ModelAndView modelAndView){
        System.out.println("user mvc controller is running...");
        // 将要返回给前端页面的数据放在modelAndView对象(本质还是放在Request请求域中)
        modelAndView.addObject("name","wanjunjie");
        // 将要浏览器页面请求转发的路径放在modelAndView对象中
        modelAndView.setViewName("happy");
        //将modelAndView模式视图对象返回给调用者,
        return modelAndView;
    }


}
