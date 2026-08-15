package com.one.anonymousinnerclass;

public class AnonymousInnerClassTest {

    public static void main(String[] args) {
        // 匿名内部类的格式: new 类名或接口名(参数列表) { 重写方法; }
        Thread myThread = new Thread() {
            @Override
            public void run() {
                System.out.println("myThread execute...");
            }
        };

        myThread.start();

        // new 类名(参数列表) 创建的是类对象
        Hello hello = new Hello();
        System.out.println(hello.sayHello());

        // 匿名内部类: 创建的是子类对象
        Hello myHello = new Hello() {
            @Override
            public String sayHello() {
                return "hello anonymousInnerClass!";
            }
        };
        System.out.println(myHello.sayHello());
    }

    public static class Hello{

        public String sayHello() {
            return "hello world!";
        }
    }
}
