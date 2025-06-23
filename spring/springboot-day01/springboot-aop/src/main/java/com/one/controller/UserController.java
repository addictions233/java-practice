package com.one.controller;

import com.one.domain.HttpResponseEntity;
import com.one.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @Description: 查询用户接口
 * @Author: one
 * @Date: 2022/04/16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 根据userId查询User用户
     * @param userId 用户id
     * @return HttpResponseEntity<User>
     */
    @GetMapping("/{userId}")
    public HttpResponseEntity<User> queryUserById(@PathVariable("userId") String userId){
        User user = new User();
        user.setUserId(userId);
        user.setName("小丑");
        return HttpResponseEntity.ok(user);
    }
}
