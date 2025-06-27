package com.one.service;


import com.one.model.User;

import java.util.List;

public interface IUserService {

    public List<User> query();

    public User save(User user);
}
