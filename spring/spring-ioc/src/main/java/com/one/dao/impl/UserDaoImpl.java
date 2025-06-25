package com.one.dao.impl;

import com.one.dao.UserDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component
@Primary
public class UserDaoImpl implements UserDao {

    public void save() {
        System.out.println("user dao is running 1111");
    }
}
