package com.one.test;

import com.one.bean.SysUser;
import com.one.service.SysUserService;
import com.one.service.impl.SysUserServiceImpl;

/**
 * @ClassName: UserTest
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/21
 */
public class UserTest {
    public static void main(String[] args) {
        SysUserService userService = new SysUserServiceImpl();
        SysUser sysUser = userService.findById((long)1);
        System.out.println(sysUser);
    }
}
