package com.corejava.extend.extend2;

/**
 * @ClassName: Executor
 * @Description: 抽象类, 抽象类是不能直接创建对象的,必须有子类继承该抽象类,并重写其中的抽象方法
 * @Author: one
 * @Date: 2022/03/19
 */
public abstract class BaseExecutor implements Executor {
    @Override
    public void fatherMethod(String words) {
        System.out.println("fatherMethod:" + words);
        sonMethod(words);
    }

    /**
     * 让子类重写的抽象方法
     * @param words words
     */
    public abstract void sonMethod(String words);
}
