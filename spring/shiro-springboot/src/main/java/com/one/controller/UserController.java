package com.one.controller;

import com.one.entity.User;
import com.one.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zjw
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(String username, String password, String rememberMe, HttpServletResponse response){
        // 执行Shiro的认证操作
        //1. 直接基于SecurityUtils获取subject主体,不需要手动的将SecurityManager和SecurityUtils手动整合，Spring已经奥丁
        Subject subject = SecurityUtils.getSubject();

        //2. 发起认证
        try {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                token.setRememberMe(rememberMe != null && "on".equals(rememberMe));
                subject.login(token);
            for (String header : response.getHeaders("Set-Cookie")) {
                header.replace("SameSite.Lax","SameSite.None; Secure");
            }
            return "SUCCESS";
        } catch (UnknownAccountException exception){
            return "username fail!!!";
        } catch (IncorrectCredentialsException exception){
            return "password fail!!!";
        } catch (AuthenticationException e) {
            return "donot know...!!!";
        }
    }




    @GetMapping("/test")
    public User test(String username){
        return userService.findByUsername(username);
    }

}
