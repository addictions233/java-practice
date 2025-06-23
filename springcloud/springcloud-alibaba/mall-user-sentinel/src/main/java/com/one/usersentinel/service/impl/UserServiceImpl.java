package com.one.usersentinel.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.one.usersentinel.dao.UserDao;
import com.one.usersentinel.entity.UserEntity;
import com.one.usersentinel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Fox
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 当SpringBoot应用接入Sentinel starter后，可以针对某个URL进行流控。所有的URL就自动成为Sentinel中的埋点资源，可以针对某个 URL 进行流控。
     * 而对于service层的资源使用@SentinelResource注解用来标识资源是否被限流、降级
     */
    @Override
    @SentinelResource(value = "getUser",blockHandler = "handleException")
    public UserEntity getById(Integer id) {
        return userDao.getById(id);
    }

    public UserEntity handleException(Integer id, BlockException ex) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("===被限流降级啦===");
        return userEntity;
    }
}
