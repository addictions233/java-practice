package com.one.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author one
 * @description 自己手动对UserDetailsService接口的实现
 * @date 2024-9-30
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 通过用户名获取用户信息
     * @param username the username identifying the user whose data is required.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟从数据库查询用户角色权限信息, 并封装成UserDetails对象返回
        return User.builder().
                username("zhangsan")
                .password(passwordEncoder.encode("123"))
                .roles("user")
                .build();
    }
}
