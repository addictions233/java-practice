package com.one.dao;

import com.one.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 在Spring中给我们提供的JPA对持久层框架做了统一的封装，
 * 而且本质上就是基于HibernateJPA来实现的，
 * 所以我们在使用的时候也可以通过SpringDataJPA的API来操作
 * dao的接口只需要继承JpaRepository接口即可
 */
public interface IUserDao extends JpaRepository<User,Integer> {
}
