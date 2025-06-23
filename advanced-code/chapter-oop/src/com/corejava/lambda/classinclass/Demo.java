package com.corejava.lambda.classinclass;

public class Demo {
    public static void main(String[] args) {
        // 创建内部类对象
//        OuterClass.InnerClass innerClass = new OuterClass().new InnerClass();
//        innerClass.innerMethod();

        // 创建静态内部类对象
        OuterClass.StaticInnerClass staticInnerClass = new OuterClass.StaticInnerClass();
        staticInnerClass.say();
    }
}

