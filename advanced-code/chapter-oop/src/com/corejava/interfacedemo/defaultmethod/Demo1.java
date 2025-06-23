package com.corejava.interfacedemo.defaultmethod;

/**
 *  如果解决默认方法在接口和超类同时定义的冲突?
 *      1, 如果超类和接口中同时定义了该默认方法,优先调用超类中的方法
 *      2, 如果实现的两个接口同时定义了默认方法,则该实现类必须重写该方法
 */
public class Demo1 extends SuperClass implements Interface1{
    public static void main(String[] args) {
        Demo1 demo1 = new Demo1();
        demo1.method();  // 调用父类中的method方法

        System.out.println("-------------");

        Demo2 demo2 = new Demo2();
        demo2.method();  // 输出: 调用子类重写的方法
    }
}

// 接口与接口中的默认方法冲突,其实现类必须重写
class Demo2 implements Interface1,Interface2{
    public void method(){
        System.out.println("调用子类重写的方法");
    }

}


