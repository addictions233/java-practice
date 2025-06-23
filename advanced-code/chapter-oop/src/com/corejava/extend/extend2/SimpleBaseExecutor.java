package com.corejava.extend.extend2;

/**
 * @ClassName: SimpleBaseExecutor
 * @Description: 抽象类的子类
 * @Author: one
 * @Date: 2022/03/19
 */
public class SimpleBaseExecutor extends BaseExecutor {
    @Override
    public void sonMethod(String words) {
        System.out.println("sonMethod:" + words);
    }
}
