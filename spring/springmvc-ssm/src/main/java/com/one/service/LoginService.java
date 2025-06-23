package com.one.service;


import com.one.domain.LoginDTO;

import javax.servlet.http.HttpSession;

/**
 * @author one
 */
public interface LoginService {
    /**
     * 登录认证
     *
     * @param loginDTO 入参DTO
     * @param httpSession http会话
     * @return String
     */
    String Login(LoginDTO loginDTO, HttpSession httpSession);
}
