package com.one.annotation3;

/**
 * @ClassName: TestMain
 * @Description: 测试自定义注解
 * @Author: one
 * @Date: 2020/12/16
 */
@HelloAnnotation(say="Do it!")
public class TestMain {
    public static void main(String[] args) {
        System.setProperty("itheima.misc.ProxyGenerator.saveGeneratedFiles","true");
        // 获取类上的注解
        HelloAnnotation helloAnnotation = TestMain.class.getAnnotation(HelloAnnotation.class);
        String say = helloAnnotation.say();
        //输出: Do it!12345678
        System.out.println(say);
    }
}
