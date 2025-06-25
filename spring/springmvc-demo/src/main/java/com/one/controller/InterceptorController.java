package com.one.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: InterceptorController
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/08
 */
//@Controller
public class InterceptorController {
    @RequestMapping("/showPage")
    public String showPage(){
        System.out.println("01Controller中的showPage()方法执行了....");
        return "page.jsp";
    }
}
