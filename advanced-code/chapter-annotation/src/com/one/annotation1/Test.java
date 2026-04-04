package com.one.annotation1;

import java.lang.reflect.Method;

/**
 * @ClassName: Test
 * @Description: 在该类中测试自定义注解
 * @Author: one
 * @Date: 2020/12/16
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Class<Test> cls = Test.class;
        Method method = cls.getMethod("main", String[].class);
        Hello hello = method.getAnnotation(Hello.class);
    }
}
