package com.one.springsecuritydemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * @author one
 * @description 普通的请求资源
 * @date 2023-2-1
 */
@RestController
public class HelloController {

    /**
     * 测试对url的访问需要进行认证和授权: 所有用户都能访问
     */
    @RequestMapping("/hello")
    @PermitAll // JSR-250注解
    public String hello() {
        System.out.println("hello spring security");
        // 使用SecurityContextHolder获取用户的登陆信息
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        // 获取身份信息, 结果是spring security封装的User对象
        User user = (User)authentication.getPrincipal();
        // 获取用户身份信息
        System.out.println("身份信息:" + user);
        // 获取权限信息
        System.out.println("权限信息:" + authentication.getAuthorities());
        new Thread(() -> {
            // 默认采用ThreadLocal存在登录用户信息, 所以子线程是无法获取的
            SecurityContext context = SecurityContextHolder.getContext();
            System.out.println("子线程获取登录用户信息:" + context.getAuthentication());
        }).start();
        return "hello spring security";
    }

    /**
     * 测试url访问需要进行认证和授权: 只用USER角色的用户才能访问
     */
    @RequestMapping("/hello/user")
//    @RolesAllowed("USER")  // JSR-250注解
//    @Secured({"ROLE_USER"})  // Spring Security注解
    @PreAuthorize("hasRole('USER')") // Spring EL表达式
    public String helloUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello" + name;
    }

    /**
     * 测试url访问需要进行认证和授权: 只有ADMIN的角色才能访问该路径
     */
    @RequestMapping("/hello/admin")
//    @RolesAllowed("ADMIN") // JSR-250注解
//    @Secured({"ROLE_ADMIN"}) // Spring Security注解
    @PreAuthorize("hasRole('ADMIN')") // Spring EL表达式
    public String helloAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello" + name;
    }

}
