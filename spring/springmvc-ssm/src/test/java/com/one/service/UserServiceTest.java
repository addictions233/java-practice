package com.one.service;

import com.github.pagehelper.PageInfo;
import com.one.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ClassName: UserServiceTest
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/09
 */
@RunWith(SpringJUnit4ClassRunner.class)   //加载junit专用的类加载器
@ContextConfiguration(locations = "classpath:applicationContext.xml")  //加载Spring容器核心配置文件
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testUserService() {
        PageInfo<User> pageInfo = userService.getByPage(1, 2);
        List<User> list = pageInfo.getList();
        System.out.println(list.get(0));
        System.out.println(list.get(1));
    }
}
