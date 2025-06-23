package com.corejava.innerclass.innerclass3;

public class Main {
    public static void main(String[] args) {

        /**
         * 使用匿名内部类创建内部类的对象
         */
        OuterClass.InnerClass innerClass = new OuterClass.InnerClass(){
            @Override
            public void method(){
                System.out.println("内部类中的method方法执行了....");
            }
        };
        innerClass.method();
    }
}
