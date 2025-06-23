package com.one.service;

import com.one.bean.SysUser;

import java.util.List;


public interface SysUserService {
    /**
     * 查询所有
     */
    public List<SysUser> selectAll();

    /**
     * 通过 id查询
     */
    public SysUser findById(Long id);

    /**
     * 插入一条数据
     */
    public Integer insert(SysUser sysUser);

    /**
     * 修改一条数据
     */
    public Integer update(SysUser sysUser);

    /**
     * 删除一条数据
     */
    public Integer delete(Long id);
}
