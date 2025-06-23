package com.one.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName: JunitDemo1
 * @Description: junit单元测试的使用:
 *      1, 单元测试类必须是public修饰的类,否则不会被发现
 *      2, 单元测试方法必须是public休息的无参数,无返回值的非静态方法
 *      3, 使用@Test @Before @After注解等方法注解
 * @Author: one
 * @Date: 2021/06/21
 */
public class JunitDemo1 {
    private String str = "aaa";
    @Before
    public void setValue() {
        str = "bbb";
    }

    @Test
    public void testJunit() {
        System.out.println(str);
    }

    @After
    public void afterTest() {
        str = "ccc";
        System.out.println(str);
    }
}
