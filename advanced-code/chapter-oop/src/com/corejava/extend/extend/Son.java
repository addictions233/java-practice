package com.corejava.extend.extend;

/**
 * @ClassName: Son
 * @Description: 子类
 * @Author: one
 * @Date: 2021/07/29
 */
class Son extends Father {
    String name;
    int age;
    Son son1 = this;
    // Father father1 = super;

    /**
     * 重写父类中的方法
     */
    @Override
    public void show() {
        System.out.println("调用子类的show方法");
    }
}
