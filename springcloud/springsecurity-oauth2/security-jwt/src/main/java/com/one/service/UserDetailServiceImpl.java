package com.one.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author one
 * @description 自定义登录认证的UserDetailsService
 * @date 2024-10-1
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 通过username从数据库获取用户信息
        return new User("zhangsan","{noop}123",
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin,ROLE_user"));
    }
}
