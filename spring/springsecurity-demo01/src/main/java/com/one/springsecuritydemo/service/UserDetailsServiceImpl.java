package com.one.springsecuritydemo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.one.springsecuritydemo.mapper.UserDetailMapper;
import com.one.springsecuritydemo.domain.pojo.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: SecurityUserDetailsService
 * @Description: 自定义登录认证service,必须实现UserDetailsService接口, 默认的采用内存的认证方式进行用户名密码登录(不适用生产)
 *               使用自定义的数据库比对方式进行认证实现,去数据库中查询用户名和密码,并返回对应的User对象
 * @Author: one
 * @Date: 2021/02/22
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    @Autowired
    private UserDetailMapper userMapper;
    /**
     * 基于数据库查询的认证方式来进行用户名和密码验证登录
     * 如果spring security配置类中采用的是UserDetailService进行认证, 则每次登陆认证都会到达本方法进行认证
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 构建一个权限集合
        List<GrantedAuthority> authorization  = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
//        List<GrantedAuthority> authorization  = AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN,USER");
        // 自定义基于数据库用户表的用户管理
        // 用户名和密码从数据表中读取
        LambdaQueryWrapper<UserDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDetail::getUsername,username);
        UserDetail userDetail = this.userMapper.selectOne(queryWrapper);
        if( null != userDetail){
            // 如果根据用户输入的用户名可用查询到用户, 然后封装为Spring Security中的User对象并返回
            // User对象实现了UserDetails接口, 我们可自定义UserDetails接口的实现类
            return User.builder()
                    .username(userDetail.getUsername())
                    .password(userDetail.getPassword())
                    // 设置用户的权限
                    .authorities(authorization)
                    .build();
        } else {
            // 如果根据用户名查询不到用户,就抛出一个异常
            throw new RuntimeException("用户名不存在!");
        }
    }
}
