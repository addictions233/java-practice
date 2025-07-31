package com.one.controller;

import com.one.pojo.SystemUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @ClassName: UserController
 * @Description: Controller层
 * @Author: one
 * @Date: 2021/06/04
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @PostMapping("save")
    public void saveUser(@RequestBody @Valid SystemUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(obj -> System.out.println(obj.getDefaultMessage()));
        }
        System.out.println(user);
    }

    @PostMapping("update")
    public String updateUser(@RequestBody @Valid SystemUser user) {
        System.out.println(user);
        return "aaa";
    }


}
