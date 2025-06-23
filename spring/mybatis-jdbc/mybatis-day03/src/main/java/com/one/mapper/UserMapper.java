package com.one.mapper;

import com.one.bean.SysUser;

import java.util.List;

/**
 * @InterfaceName: UserMapper
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/21
 */
public interface UserMapper {
    /**
     * 根据ID查询用户
     */
    SysUser findById(Long id);

    /**
     * 查询所有
     */
    List<SysUser> findAll();
}
