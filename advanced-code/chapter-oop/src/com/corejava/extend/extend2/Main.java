package com.corejava.extend.extend2;

/**
 * @ClassName: Main
 * @Description: 测试类
 * @Author: one
 * @Date: 2022/03/19
 */
public class Main {
    public static void main(String[] args) {
        // 子类是继承了父类的非私有成员的,所以SimpleBaseExecutor是继承了父类BaseExecutor中的fatherMethod()方法
        Executor executor = new SimpleBaseExecutor();
        executor.fatherMethod("hello java");

        String str = "aaa";
        String str2 = new String(str);
        System.out.println( str == str2);
    }
}
