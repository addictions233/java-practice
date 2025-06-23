package com.one.domain;

/**
 * @ClassName: User
 * @Description: pojo类 用来判断SpringBoot能否直接获取第三方jar包中的bean对象
 * @Author: one
 * @Date: 2021/03/28
 */
public class User {
    private String name;
    private String age;
    public void say(){
        System.out.println("hello,this is User");
    }
}
