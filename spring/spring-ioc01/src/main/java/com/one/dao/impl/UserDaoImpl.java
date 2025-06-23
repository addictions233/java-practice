package com.one.dao.impl;

import com.one.dao.UserDao;

/**
 * @ClassName: UserDaoImpl
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/01
 */
public class UserDaoImpl implements UserDao {
    private String name;

    public UserDaoImpl() {
        System.out.println("UserDaoImpl 对象被创建了");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void save(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "UserDaoImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
