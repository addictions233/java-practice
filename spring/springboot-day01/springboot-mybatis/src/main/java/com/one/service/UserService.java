package com.one.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.one.pojo.User;

import java.util.List;

/**
 * @InterfaceName: UserService
 * @Description: service层接口
 * @Author: one
 * @Date: 2020/12/15
 */
public interface UserService {

    /**
     * 使用异步线程查询所有User用户
     *
     * @return List<User>
     */
    List<User> findAllUser();

    /**
     * 使用CompleterFuture查询所有的User用户
     * @return List<User>
     */
    List<User> completeFutureFindAllUser();

    /**
     * 分页查询
     *
     * @param dataBody 入参
     * @return PageInfo
     */
    PageInfo<User> findByPage(JSONObject dataBody);

    /**
     * 根据id查询User
     *
     * @param id 主键id
     * @return User
     */
    User selectById(Integer id);

    /**
     * 新增一条数据
     *
     * @param user User对象
     */
    void insert(User user);

    /**
     * 修改一条数据
     *
     * @param user User对象
     */
    void update(User user);

    /**
     * 根据id删除一条数据
     *
     * @param id 主键id
     */
    void deleteById(Integer id);
}
