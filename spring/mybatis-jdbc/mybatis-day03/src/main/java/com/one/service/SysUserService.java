package com.one.service;

import com.one.bean.SysUser;

import java.util.List;


public interface SysUserService {
    /**
     * 查询所有
     */
    List<SysUser> selectAll();

    /**
     * 通过 id查询
     */
    SysUser findById(Long id);

    /**
     * 测试ResultHandler处理结果
     * @param sysUser sysUser
     */
    void getSystemUsers(SysUser sysUser);

    /**
     * 插入一条数据
     */
    Integer insert(SysUser sysUser);

    /**
     * 修改一条数据
     */
    Integer update(SysUser sysUser);

    /**
     * 删除一条数据
     */
    Integer delete(Long id);
}
