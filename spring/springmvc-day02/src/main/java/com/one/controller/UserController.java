package com.one.controller;

import com.one.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/07
 */
@Controller
@RequestMapping("userController")
public class UserController {
    @RequestMapping("/testJson01")      //userController/testJson01
    @ResponseBody   //将方法的返回值写回到客户端浏览器
    public User testJson01(@RequestBody User user){
        System.out.println("testJson01方法执行了...");
        System.out.println(user);
        return user;
    }
}
