package com.one.mapper;

import com.one.bean.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;

/**
 * @InterfaceName: UserMapper
 * @Description: mapper层接口
 * @Author: one
 * @Date: 2020/12/21
 */
public interface UserMapper {
    /**
     * 根据ID查询用户
     */
    SysUser findById(String id);

    /**
     * 查询所有
     */
    List<SysUser> findAll();


    void getSysUsers(@Param("dto") SysUser sysUser, ResultHandler<SysUser> resultHandler);
}
