package com.one.annotation3;

import java.lang.annotation.Annotation;

/**
 * @ClassName: TestMain
 * @Description: 测试自定义注解
 * @Author: one
 * @Date: 2020/12/16
 */
@HelloAnnotation(say="Do it!")
public class TestMain {
    public static void main(String[] args) {
        System.setProperty("com.one.ProxyGenerator.saveGeneratedFiles","true");
        // 获取类上的注解
        HelloAnnotation helloAnnotation = TestMain.class.getAnnotation(HelloAnnotation.class);
        // 所有的注解类型都是 Annotation 接口的实现类
        if (helloAnnotation instanceof Annotation annotation) {
            // 输出: com.one.annotation3.HelloAnnotation
            System.out.println(annotation.annotationType());
        }
        String say = helloAnnotation.say();
        //输出: Do it!12345678
        System.out.println(say);
    }
}
