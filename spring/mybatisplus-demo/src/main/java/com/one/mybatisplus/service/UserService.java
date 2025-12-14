package com.one.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.one.mybatisplus.entity.TbUser;

import java.util.List;

/**
 * @InterfaceName: UserService
 * @Description: 使用mybatis plus提供的service层的方法
 * @Author: one
 * @Date: 2022/04/13
 */
public interface UserService extends IService<TbUser> {
    /**
     * 通过id查询一条User数据
     * @param id 主键id
     * @return com.one.mybatisplusspringboot.pojo.User
     */
    TbUser selectOneUser(Long id);

    /**
     * 使用mybatis plus在service层进行分页查询
     * @param pageNumber 当前页码
     * @param pageSize 每页条数
     * @return List<User>
     */
    List<TbUser> findUserByPage(Integer pageNumber, Integer pageSize);


    List<?> selectUser();


    void updateUser(TbUser user);

    int insertUser(TbUser user);
}
