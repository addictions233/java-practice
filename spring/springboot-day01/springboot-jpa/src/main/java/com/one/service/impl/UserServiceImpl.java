package com.one.service.impl;


import com.one.dao.IUserDao;
import com.one.model.User;
import com.one.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao dao;

    @Override
    public List<User> query() {
        PageRequest pageRequest = PageRequest.of(1, 3);
        dao.findAll(null,pageRequest);
        return dao.findAll();
    }

    @Override
    public User save(User user) {
        return dao.save(user);
    }
}
