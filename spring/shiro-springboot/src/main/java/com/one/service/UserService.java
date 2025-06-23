package com.one.service;

import com.one.entity.User;

/**
 * @author zjw
 * @description
 */
public interface UserService {

    User findByUsername(String username);

}
