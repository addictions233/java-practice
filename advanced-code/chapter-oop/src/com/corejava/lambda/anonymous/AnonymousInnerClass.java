package com.corejava.lambda.anonymous;

/**
 * @author one
 * 匿名内部类: 推荐使用的优先级: 方法引用 > lambda表达式 > 匿名内部类
 */
public class AnonymousInnerClass {
    public static void main(String[] args) {
        InnerA inner = new InnerA() {
            @Override
            public void show() {
                System.out.println("调用匿名内部类方法");
            }
        };
        inner.show(); // 输出: 调用匿名内部类方法
    }
}
