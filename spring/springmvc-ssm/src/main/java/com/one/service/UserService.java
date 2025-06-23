package com.one.service;

import com.github.pagehelper.PageInfo;
import com.one.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @InterfaceName: UserService
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/09
 */
@Transactional(readOnly = true) //将事务管理开在业务层的接口中
public interface UserService {
    /**
     * @description 通过id查询用户
     * @param
     * @return
     */
    User get(Integer uuid);

    /**
     * @description 查询所有的User
     * @param
     * @return
     */
    List<User> getAll();
    
     /**
      * @description 分页查询    
      * @param
      * @return 
      */
    PageInfo<User> getByPage(int page,int size);

    /**
     * @description 增
     * @param
     * @return
     */
    @Transactional(readOnly = false)
    boolean save(User user);


    /**
     * @description 删
     * @param
     * @return
     */
    @Transactional(readOnly = false)
    boolean delete(Integer uuid);

    /**
     * @description 改
     * @param
     * @return
     */
    @Transactional(readOnly = false)
    boolean update(User user);

    /**
     * @description 根据用户名和密码进行登录
     * @param
     * @return
     */
    User login(String userName, String password);

}
