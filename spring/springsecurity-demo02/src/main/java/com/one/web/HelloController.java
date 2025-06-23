package com.one.web;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * @ClassName
 * @Description
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello "+name;
    }

    /**
     * 必须用户USER角色才能访问
     */
    @RequestMapping("/hello/user")
    @PreAuthorize("hasRole('USER')")
    public String helloUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello-user:" + name;
    }

    /**
     * 必须用户ADMIN角色才能访问
     */
    @RequestMapping("/hello/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello-admin:" + name;
    }



    public static void main(String[] args) {
        String pass = BCrypt.hashpw("000000", BCrypt.gensalt());
        System.out.println(pass);

    }

}
