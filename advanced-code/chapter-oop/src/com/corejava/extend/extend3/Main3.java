package com.corejava.extend.extend3;

/**
 * @ClassName: Main3
 * @Description: 测试继承中的get,set方法:
 * @Author: one
 * @Date: 2022/05/24
 */
public class Main3 {
//    public static void main(String[] args) {
//        Apple apple = new Apple();
//        apple.setName("苹果");
//        Fruit fruit = apple;
//        // 本质是调用子类Apple类中的getName()方法
//        System.out.println(fruit.getName());
//    }

    /**
     * 如果父类构造器中调用被子类重写的方法，会导致子类重写的方法在子类成员变量初始化之前和构造器执行之前执行，
     * 从而导致子类重写的方法无法访问到子类实例变量的值，因为此时这些变量还没有被初始化。
     */
    public static void main(String[] args) {
        /**
         * 打印结果:
         *      Daughter:null
         *      father:father
         *      Daughter:Daughter
         *      father:father
         */
        Daughter daughter = new Daughter();
    }
}
