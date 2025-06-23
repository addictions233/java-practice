package com.one.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.one.dao.UserDetailMapper;
import com.one.domain.LoginDTO;
import com.one.domain.UserDetail;
import com.one.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author one
 * @description 登录认证的service层实现
 * @date 2022-9-18
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements LoginService {
    @Autowired
    private UserDetailMapper userDetailMapper;

    /**
     * 登录认证
     *
     * @param loginDTO 入参DTO
     * @param httpSession http会话
     * @return String
     */
    @Override
    public String Login(LoginDTO loginDTO, HttpSession httpSession) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("用户名或者密码为空");
        }
        LambdaQueryWrapper<UserDetail> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserDetail::getUsername,username);
        UserDetail userDetail = userDetailMapper.selectOne(queryWrapper);
        if (Objects.isNull(userDetail)) {
            return "输入的用户名不存在";
        }
        if (!StringUtils.equals(userDetail.getPassword(),password)) {
            return "输入用户名或者密码不存在";
        }
        // 将用户信息存储在session会话中
        if (userDetail.getUsername().equals("zhangsan")) {
            userDetail.setAuthorities(new HashSet<String>() {{
                add("/visit");
            }});
        } else {
            userDetail.setAuthorities(new HashSet<>());
        }
        httpSession.setAttribute(UserDetail.SESSION_USER_KEY, userDetail);
        return userDetail.getUsername() + "登录成功";
    }
}
