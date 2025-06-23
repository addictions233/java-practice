package com.one.controller;


import com.one.domain.LoginDTO;
import com.one.domain.UserDetail;
import com.one.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @ClassName: TestController
 * @Description: 使用自定义的controller简单的模拟spring security
 * @Author: one
 * @Date: 2021/02/22
 */
@RestController
@RequestMapping("/userDetail")
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 使用用户名和密码实现最基础的登录认证, 会话管理
     *
     * @param loginDTO 用户名和密码
     * @return String
     */
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginDTO loginDTO, HttpSession httpSession) {
        return loginService.Login(loginDTO, httpSession);
    }

    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }

    @GetMapping("/visit")
    public String visit(HttpSession httpSession) {
        // HttpSession中存储attributes使用的是ConcurrentHashMap数据结构, 所以每个Session应该使用位于的key
        UserDetail userDetail = (UserDetail) httpSession.getAttribute(UserDetail.SESSION_USER_KEY);
        if (Objects.isNull(userDetail)) {
            return "匿名访问";
        } else {
            return userDetail.getUsername() + "进行了访问";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "已退出登录";
    }
}
