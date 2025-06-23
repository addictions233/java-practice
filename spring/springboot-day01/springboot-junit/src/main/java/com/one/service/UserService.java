package com.one.service;

import com.one.mapper.SystemUserMapper;
import com.one.pojo.SystemUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/17
 */
@Service
public class UserService {
    @Resource
    private SystemUserMapper systemUserMapper;

    public void run() {
        System.out.println("userService is running.....");
    }

    public Integer insertSystemUser(SystemUser systemUser) {
        return systemUserMapper.insertUser(systemUser);
    }
}
