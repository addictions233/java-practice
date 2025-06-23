package com.one.test;

import com.one.SpringbootJunitApplication;
import com.one.mapper.SystemUserMapper;
import com.one.pojo.SystemUser;
import com.one.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @ClassName: UserServiceTest
 * @Description: 测试springboot中集成单元测试
 * @Author: one
 * @Date: 2020/12/17
 */
@RunWith(SpringRunner.class)  // 是一个测试启动器, 可以加载springboot测试注解, 在测试在spring容器环境下执行, 如果没此注解,将导致userService等bean对象注入失败,
// 高版本可不加此注解
@SpringBootTest(classes = SpringbootJunitApplication.class) // 目的是加载ApplicationContext, 启动spring容器
public class UserServiceTest {

    @Autowired
    @InjectMocks
    private UserService userService;

    @Mock
    private SystemUserMapper systemUserMapper;

    @Before
    public void setup() {
        //添加Mock注解初始化
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserService() {
        userService.run();
    }

    /**
     * 测试Springboot提供的Assert工具类
     * Assert工具类中大约有30多个静态方法供外部类调用，它的特点就是符合条件继续执行，否则抛出IllegalArgumentException异常
     * 可以编写自己的断言工具类,当判断失败时,抛出直接的异常
     */
    @Test
    public void springAssertTest() {
        String str = "abc";
        Assert.hasLength(str,"字符串不能为空");
        int count = 1;
        // Mock, 不真实调用，而是将外调的接口、数据库层面都Mock返回自己想要的各类假数据
        Mockito.when(systemUserMapper.insertUser(new SystemUser())).thenReturn(count);
        Integer result = userService.insertSystemUser(new SystemUser());
        System.out.println(result);

    }
}
