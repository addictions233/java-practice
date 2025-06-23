package com.corejava.lambda.lambdatest3;

/**
 * @ClassName: LambdaTest
 * @Description: lambda表达式中使用局部变量
 * @Author: one
 * @Date: 2021/06/19
 */
public class LambdaTest {
    /**
     * 定义静态成员变量
     */
    private static int number2 = 3;

    /**
     * 定义引用数据类型的成员变量
     */
    private static String str = "aaa";

    public static void main(String[] args) {
        int number1  = 10;
        /**
         * 在Lambada表达式中使用局部变量或者成员变量的注意事项:
         *      1, 只能引用用final修饰的局部变量或者,即是不用final修饰的局部变量,在后面代码中也不能改变值
         *      2, 在lambda表达式中引用成员变量不用final修饰,后续代码也能修饰成员变量的值
         *  为什么在lambda表达式中使用局部变量有这些限制,而使用成员变量没有这些限制?
         *  第一: 实例变量和局部变量背后使用的实现逻辑不一样, 实例变量存储在堆内存中,堆是所用线程共享的,
         *      而局部变量是保存线程栈中的,如果lambda表达式中使用了局部变量,并且lambda在一个新的线程中执行
         *      那么使用lambada的线程栈可能在分别该局部变量的线程将这个变量收回之后,再去访问该变量,这样有可能访问不到
         *      为了避免这个问题,java在访问自由局部变量时,实际上是在访问它的变量副本,而不是访问原始变量,为了保证
         *      局部变量的真实值和lambda表达式中的变量副本值一致,就必须让该局部变量的值是不可变的
         *  第二: 这一限制不鼓励你使用改变lambda表达式使用的外部变量的典型命令式编程,这种模式会阻碍java8很容易做到的并行处理
         *
         */
        InterfaceA interfaceA = (() -> {
            // 在lambda表达式中使用方法的局部变量
            System.out.println(number1);
            // 在lambda表达式中使静态成员变量
            System.out.println(LambdaTest.number2);
            // 在lambda表达式中使用普通成员变量
            System.out.println(str);
        });
        interfaceA.count();
        // 这样写编译会报错,在lambda表达式中使用局部变量,该局部变量必须是用final修饰的常量或者事实上的常量
//        number1 = 20;
        // 而实际的成员变量和静态成员变量是可以在lambda表达式中引用之后改变值的
        LambdaTest.number2 = 10;
        System.out.println(LambdaTest.number2);
        str = "bbb";
    }
}
