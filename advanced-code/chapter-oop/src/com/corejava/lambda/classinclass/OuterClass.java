package com.corejava.lambda.classinclass;

class OuterClass {
    /**
     * 外部类的构造方法
     */
    public OuterClass() {
        System.out.println("调用了外部类的构造方法");
    }

    /**
     * Inner类作为Outer类的内部类, 如果内部类不使用外部类的非静态成员,那么可以将内部类定义为静态的
     * 内部类中不能拥有静态成员
     */
    class InnerClass {
//        static String name =  "123"; // 这种写法编译报错,内部类中不能有静态成员

        /**
         * 普通内部类的构造方法
         */
        public InnerClass() {
            System.out.println("调用了InnerClass类的构造方法");
        }

        public void innerMethod(){
            System.out.println("内部类方法");
        }
    }

    /**
     * 静态内部类和外部类一样都是顶级类,静态内部类的对象的存在不依赖外部类对象的存在
     * 静态类里面不能调用外部类的非静态成员
     */
    static class StaticInnerClass {
        /**
         * 静态内部类的构造方法
         */
        public StaticInnerClass() {
            System.out.println("调用了StaticInnerClass的构造方法");
        }

        public void say() {
            System.out.println("这是静态内部类中的方法");
        }
    }
}