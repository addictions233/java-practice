package com.one.controller;

import com.github.pagehelper.PageInfo;
import com.one.controller.results.Code;
import com.one.controller.results.Result;
import com.one.domain.User;
import com.one.service.UserService;
import com.one.system.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/09
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{uuid}")
    public User get(@PathVariable Integer uuid){
//        User user = userService.get(uuid);
        User user = new User();
        user.setUuid(uuid);
        user.setUserName("张三");
        System.out.println("uuid="+uuid);
        throw new BusinessException("业务异常");
//        return user;
    }

    @GetMapping("/{page}/{size}")
    public PageInfo<User> getByPage(@PathVariable int page, @PathVariable int size){
        PageInfo<User> pageInfo = userService.getByPage(page, size);
        return pageInfo;
    }

    @GetMapping
    public List<User> getAll(){
        List<User> users = userService.getAll();
        return users;
    }

    @DeleteMapping("/{uuid}")
    public boolean delete(@PathVariable Integer uuid){
        return userService.delete(uuid);
    }

    @PostMapping
    public Result save(User user){
        boolean flag = userService.save(user);
        return new Result(flag? Code.SAVE_OK:Code.SAVE_ERROR);
    }

    @PutMapping
    public Result update(User user){

        boolean flag = userService.update(user);
        return  new Result(flag? Code.UPDATE_OK:Code.UPDATE_ERROR);
    }



    @GetMapping("/{userName}/{password}")
    public User login(@PathVariable String userName, @PathVariable String password){
        return userService.login(userName, password);
    }
}
