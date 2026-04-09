package com.one.test;

import com.one.JunitApplication;
import com.one.mapper.SystemUserMapper;
import com.one.pojo.SystemUser;
import com.one.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.util.Assert;

/**
 * @ClassName: UserServiceTest
 * @Description: 测试springboot中集成单元测试
 * @Author: one
 * @Date: 2020/12/17
 */
// 高版本可不加此注解
@SpringBootTest(classes = JunitApplication.class) // 用于加载spring上下文, 启动spring容器
@ExtendWith(MockitoExtension.class)  // 启用Mockito注解, 如 @Mock注解
public class UserServiceTest {

    /**
     * 注解@InjectMocks : 创建一个实例，并将标记为 @Mock 的对象自动注入到该实例中。
     */
    @Autowired
    @InjectMocks
    private UserService userService;

    /**
     * 使用@Mock注解创建一个Mock实例（虚拟对象）
     */
    @Mock
    private SystemUserMapper systemUserMapper;

    @BeforeTestMethod
    public void setup() {
        //添加Mock注解初始化
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserService() {
        // 1. 定义Mock行为
        SystemUser mockUser = new SystemUser(1L, "张三", 23);
        Mockito.when(systemUserMapper.selectById(1L)).thenReturn(mockUser);

        // 2.调用实际方法
        SystemUser systemUser = userService.selectById(1L);

        // 3.断言结果
        assert mockUser.getName().equals(systemUser.getName());
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
