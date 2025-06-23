package com.one.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.one.dao.UserDao;
import com.one.domain.User;
import com.one.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/09
 */
@Service("/userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User get(Integer uuid) {
        return userDao.get(uuid);
    }

    @Override
    public List<User> getAll() {
       return userDao.getAll();
    }

     /**
      * @description 分页查询
      * @param
      * @return
      */
     @Override
     public PageInfo<User> getByPage(int page, int size){
         PageHelper.startPage(page,size);
         List<User> all = userDao.getAll();
         return new PageInfo<>(all);
     }

    @Override
    public boolean save(User user) {
        return userDao.save(user);
    }

    @Override
    public boolean delete(Integer uuid) {
         return userDao.delete(uuid);
    }

    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    @Override
    public User login(String userName, String password) {
        User user = userDao.getByUserNameAndPassword(userName, password);
        return user;
    }
}
