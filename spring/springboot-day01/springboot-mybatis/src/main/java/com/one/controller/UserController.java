package com.one.controller;

import com.one.pojo.User;
import com.one.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserController
 * @Description: 对用户user进行增删改查的controller层
 * @Author: one
 * @Date: 2020/12/15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/findAll")
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    /**
     * 根据id查询User
     *
     * @param id Get请求方式
     * @return User
     */
    @GetMapping("/selectById/{id}")
    public User selectById(@PathVariable("id") Integer id) {
        return userService.selectById(id);
    }

    /**
     * 新增一条数据
     *
     * @param user 入参
     */
    @PostMapping("/insert")
    public void insert(@RequestBody User user) {
        userService.insert(user);
    }

    /**
     * 修改一条数据
     *
     * @param user 入参
     */
    @PutMapping("/update")
    public void update(@RequestBody User user) {
        userService.update(user);
    }

    /**
     * 根据id删除一条数据  Delete请求方式
     *
     * @param id 入参
     */
    @DeleteMapping("/del/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        userService.deleteById(id);
    }
}
