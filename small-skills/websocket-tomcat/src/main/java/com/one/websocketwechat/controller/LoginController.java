package com.one.websocketwechat.controller;

import com.one.websocketwechat.pojos.User;
import com.one.websocketwechat.vos.LoginResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName: LoginController
 * @Description: 登录
 * @Author: one
 * @Date: 2021/05/16
 */
@RestController
public class LoginController {
    @RequestMapping("/login")
    public LoginResult login(User user, HttpSession httpSession){
        LoginResult result = new LoginResult();
        if (null != user && "123".equals(user.getPassword())){
            result.setFlag(true);
            result.setMessage("登录成功");
            // 每个客户端都对应一个HttpSession对象,一个客户端创建一个会话,而不是多个客户端公用一个会话
            httpSession.setAttribute("user",user.getUsername());
        } else {
            result.setFlag(false);
            result.setMessage("登录失败");
        }
        return result;
    }
}
