package com.corejava.innerclass.innerclass2;

/**
 *  静态内部类
 *      静态内部类不能访问外部类的非静态成员
 *      静态内部类中可以有静态属性和静态行为
 *   注意: 只要是内部类不需要访问外部类的成员,都应将内部类用static修饰为静态内部类
 */
public class ArrayAlg {
    /**
     * 定义一个方法,用来获取一个数组的最大值和最小值,并将这个两个值包装在 Pair类对象
     * 中返回
     */
    public static Pair minmax(double[] array){
        double min = Double.POSITIVE_INFINITY;  //double类型最大值
        double max = Double.NEGATIVE_INFINITY;  // double类型最小值
        //遍历一次数组,就获取最大值和最小值
        for (double v : array) {
            if(v>max) max =v;
            if(v<min) min =v;
        }
        return new Pair(min,max);
    }

    /**
     *  定义一个静态内部类 Pair , 通过该类的成员变量first和second可以用来存储两个值
     */
    public static class Pair{
        private double first;
        private double second;

        //内部类的构造方法
        public Pair(double first ,double second){
            this.first = first;
            this.second = second;
        }
        //getter方法
        public double getFirst(){
            return first;
        }

        public double getSecond(){
            return second;
        }
    }
}
