package com.one.service.impl;

import com.one.entity.User;
import com.one.mapper.UserMapper;
import com.one.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zjw
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
