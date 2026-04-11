package com.one.domain.respository;

import com.one.domain.entity.User;

/**
 * Repository: 抽象并封装外部数据的访问逻辑
 */
public interface UserRepository {
    User save(User user);
}
