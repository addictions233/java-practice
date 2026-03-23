package com.one.dao.impl;

import com.one.dao.UserDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author one
 */
@Primary
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

    @Override
    public void save() {
        System.out.println("user dao is running 1111");
    }
}
