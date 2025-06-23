package com.corejava.genericclass;

/**
 *  ArrayAlg类 :
 *      在该类中定义一个方法: Pair<T> minMax(T[] array) 获取数组array中的元素最大值和最小值
 *      并将该值封装在 Pair类对象中一并返回
 */
public class ArrayAlg {
    /**
     *  泛型方法:
     *      注意泛型方法的格式
     *      为什么该方法声明为泛型的?  因为不知道传入的数组array的元素是什么类型,所以使用泛型
     * @param array
     * @param
     * @return
     */
    public static <T extends Comparable> Pair<T> minMax(T[] array){
        if( array==null || array.length==0) return null; // 如果 if 条件判断后面不写大括号,只执行后面一句话
        T min = array[0];
        T max = array[0];
        for (T t : array) {
            min = t.compareTo(min)>0? min:t;
            max = t.compareTo(max)>0? t:max;

        }
        return new Pair<>(min,max);
    }
    /**
     *  定义一个内部类 Pair, 目的用其成员变量存储两个值
     *      因为该类不用用到其外部类的成员,所以用static修饰
     *      想让该类存储的元素类型和minMax(T[] array)数组元素类型相同,所以使用泛型类
     */
    public static class Pair<T>{
        private T first;
        private T second;

        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public T getSecond() {
            return second;
        }
    }
}
