package com.corejava.lambda.lambdatest;

/**
 * @author one
 *  测试使用Lambda表达式:
 *      lamda表达式本质还是一个函数式接口的实现类对象,
 *      一般我们创建接口的对象(接口是没法直接创建对象的)需要分下面两步:
 *             1, 写一个类实现该接口
 *             2, 利用多态, 创建该接口的的实现类的对象赋值接口对象
 *      匿名内部类: 省略了创建接口的实现类这一步,之间将接口的实现类对象赋值给接口对象
 *      lambda表达式: 省略了创建接口的实现类型对象,直接利用方法体替代对象(函数式编程)
 *      方法引用: 省略了用lambda表达式创建方法, 直接利用现有类的方法替换lambda表达式定义的方法体
 */
public class Demo1 {
    public static void main(String[] args) {
        /**
         * 使用匿名内部类创建接口的实现类对象,并用该对象作为方法形参
         */
        useInterA(new InterA() {
            @Override
            public void show() {
                System.out.println("调用匿名内部类重写的show()方法");
            }
        });

        /**
         * 使用lambda表达式作为接口得实现类型对象, 并用该对象座位费方法的形参
         */
        useInterA(()-> System.out.println("调用lambda表达式中重写的show()方法"));

        useInterB(()->{ int x = 10+20;
        return x+30;});

    }

    /**
     * 定义两个方法, 方法的形参是接口对象
     * @param a a
     */
    public static void useInterA(InterA a){
        a.show();
    }

    public static void useInterB(InterB b){
        System.out.println(b.getNum());
    }
}
