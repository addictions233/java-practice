package com.corejava.genericclass;

/**
 * ArrayAlg类 :
 * 在该类中定义一个方法: Pair<T> minMax(T[] array) 获取数组array中的元素最大值和最小值
 * 并将该值封装在 Pair类对象中一并返回
 */
public class ArrayAlg {

    /**
     * 泛型方法:
     * 注意泛型方法的格式
     * 为什么该方法声明为泛型的?  因为不知道传入的数组array的元素是什么类型,所以使用泛型
     *
     * @param array 传入的泛型数组
     * @return 泛型数组中的 最大值和最小值
     * <?> 表示无限制泛型通配符
     * <? extends T> extends 关键字声明了类型的上界, 表示参数化的类型可能是指定的类型T, 或者是此类型的子类
     * <? super E> super关键字声明了类型的下界, 表示参数化的类型可能是指定的类型T, 或者此类型的父类
     */
    public static <T extends Comparable<? super T>> Pair<T> minMax(T[] array) {
        if (array == null || array.length == 0) return null; // 如果 if 条件判断后面不写大括号,只执行后面一句话
        T min = array[0];
        T max = array[0];
        for (T t : array) {
            min = t.compareTo(min) > 0 ? min : t;
            max = t.compareTo(max) > 0 ? t : max;

        }
        return new Pair<>(min, max);
    }
}
