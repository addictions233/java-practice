package com.one.service.impl;

import com.one.dao.UserDao;
import com.one.service.UserService;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/01
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    /**
     * userDao属性的set方法
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String toString() {
        return "UserServiceImpl{" +
                "userDao=" + userDao +
                '}';
    }

    public UserServiceImpl(){
        System.out.println("UserServiceImpl对象被创建了");
    }

    public void initMethod(){
        System.out.println("init方法被调用了!");

    }

    public void destroyMethod(){
        System.out.println("destroy方法被调用了!");
    }

    public void save() {
        System.out.println("user service is running!");
    }


}
