package com.one.controller;

import com.one.annotation.AdminOnly;
import com.one.domain.HttpResponseEntity;
import com.one.domain.User;
import com.one.exception.ApplicationException;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: CheckUserController
 * @Description: 进行用户登录校验
 * 执行流程:请求-->filter过滤器-->interceptor拦截器-->ControllerAdvice(全局异常处理器)-->AOP-->controller方法
 * @Author: one
 * @Date: 2021/04/03
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    /**
     * 调用登录接口
     * @param user 登录用户
     * @return String
     */
    @PostMapping()
    @AdminOnly(program = "student") // 使用aop进行鉴权,只有student角色才能访问本接口
    public HttpResponseEntity<String> checkUser(@RequestBody User user) {
        System.out.println(user);
        System.out.println("进行了用户登录校验");
        throw new ApplicationException(501,"强行抛出异常");
//        return HttpResponseEntity.ok("登录成功!");
    }
}
