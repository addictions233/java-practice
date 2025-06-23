package com.one.dao.impl;

import com.one.dao.UserDao;
import org.springframework.stereotype.Component;

/**
 * @ClassName: UserDaoImpl2
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/02
 */
@Component("userDao2")
public class UserDaoImpl2 implements UserDao {
    @Override
    public void save() {
        System.out.println("userDao is running 22222");
    }
}
